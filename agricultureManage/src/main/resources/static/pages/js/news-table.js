var myeditor ;
        window.onload = function()
            {
                myeditor = CKEDITOR.replace( 'news_content');
                detail_editor = CKEDITOR.replace( 'detail_news_content');
                CKEDITOR.replace( 'description');
            };

        var vm = new Vue({
            el: "#newsTable",
            data: {
                newsList: [],
                newsDetail: "",
                permission: "",
                nodeList: "",
                id : "",
                selectClass : ""
            },
            methods: {
                ceshi:function(){
                    alert(vm.newsDetail)
                },
                change : function(){
                    let param = new URLSearchParams()
                param.append("newsId" ,this.id)
                param.append("newsTitle" , $("#news_title").val())
                param.append("newsContent" , myeditor.document.getBody().getHtml())
                if($("#allNode").val() != null){
                    param.append('classId' , $("#allNode").val())
                }
                axios
                .post("http://localhost:8085/biz/news/update",param)
                .then(response => {
                    alert("修改成功");
                    location.reload();
                })
                },
                showAdd : function(){
                    $('#addModal').modal();
                },
                upload:function(){
                let param = new URLSearchParams()
                param.append('newsContent', CKEDITOR.instances.description.getData()),
                param.append("newsTitle" , this.$refs.newsTitle.value),
                param.append("newsTag" , this.$refs.newsTag.value)
                if($("#add_allNode").val() != null){
                    param.append('classId' , $("#add_allNode").val())

                    axios
                    .post("http://localhost:8085/biz/news/add",param
                    )
                    .then(response => {
                        if(response.data.code == '200'){
                            alert("上传成功")
                        }else if(response.data.code == '400'){
                            alert(response.data.msg)
                        }
                    })
                }else{
                    alert("新闻分类不能为空")
                }
                
                
            }
            },
            mounted(){
                axios
                .get('http://localhost:8085/biz/news/page',{
                    params: {
                        "page" : 1,
                        "count" : 7
                    }
                })
                .then(response => {
                    window.total = response.data.data.pages
                    for(var i = 0;i < response.data.data.records.length;i++) {
                        vm.newsList.push(response.data.data.records[i]),
                        vm.newsDetail += '<tr class="gradeA" ><td>'+this.newsList[i].newsId+'</td><td style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;width50px">'+this.newsList[i].newsTitle+'</td><td>'+this.newsList[i].newsContent.substring(0,15)+"..."+'</td><td>'+this.newsList[i].gmtModify+'</td><td>'+this.newsList[i].gmtCreate+'</td><td><button class="btn btn-info detail" id='+i+'>详情</button>  <button class="btn btn-warning update" id='+i+'>修改</button>   <button class="btn btn-danger delete" id='+i+'>删除</button></td></tr>';
                    }




                    //给修改按钮添加事件
                    this.$nextTick().then(() => {
                          $('.update').on('click',function(){
                            vm.id = vm.newsList[$(this).attr("id")].newsId;
                            axios
                                .get("http://localhost:8085/biz/newsClass/queryClassByNewsId",{
                                    params : {
                                        "newsId" : vm.id
                                    }
                                })
                                .then(response => {
                                    vm.selectClass = "";
                                    for(var i = 0;i < response.data.data.length;i++) {
                                        vm.selectClass += " "+response.data.data[i].name
                                    }
                                })
                            $("#myModalLabel").text("修改");
                            $("#news_title").val(vm.newsList[$(this).attr("id")].newsTitle);
                            // $("#news_content").val(vm.newsList[$(this).attr("id")].newsContent);
                            myeditor.setData(vm.newsList[$(this).attr("id")].newsContent);
                            $("#creator").val(vm.newsList[$(this).attr("id")].gmtModify);
                            $('#myModal').modal();
                            
                         })

                    })

                    //给详情按钮添加事件
                    this.$nextTick().then(() => {
                          $('.detail').on('click',function(){
                            vm.id = vm.newsList[$(this).attr("id")].newsId;
                            axios
                                .get("http://localhost:8085/biz/newsClass/queryClassByNewsId",{
                                    params : {
                                        "newsId" : vm.id
                                    }
                                })
                                .then(response => {
                                    vm.selectClass = "";
                                    for(var i = 0;i < response.data.data.length;i++) {
                                        vm.selectClass += " "+response.data.data[i].name
                                    }
                                })
                            $("#detailModalLabel").text("详情");
                            $("#detail_news_title").val(vm.newsList[$(this).attr("id")].newsTitle);
                            // $("#news_content").val(vm.newsList[$(this).attr("id")].newsContent);
                            detail_editor.setData(vm.newsList[$(this).attr("id")].newsContent);
                            $("#detail_creator").val(vm.newsList[$(this).attr("id")].gmtModify);
                            $('#detailModal').modal();
                            vm.id = vm.newsList[$(this).attr("id")].newsId;
                         })

                    })

                    //给删除按钮添加事件
                    this.$nextTick().then(() => {
                          $('.delete').on('click',function(){
                            axios
                                .get("http://localhost:8085/biz/news/delete",{
                                    params : {
                                        "newsId" :  vm.newsList[$(this).attr("id")].newsId
                                    }
                                })
                                .then(response => {
                                    alert("success")
                                    location.reload();
                                })
                         })

                    })
                    


                    $('#pageLimit').bootstrapPaginator({
                         currentPage: 1,//当前的请求页面。
                         totalPages: window.total,//一共多少页。
                         size:"normal",//应该是页眉的大小。
                         bootstrapMajorVersion: 3,//bootstrap的版本要求。
                         alignment:"right",
                         numberOfPages:5,//一页列出多少数据。
                         itemTexts: function (type, page, current) {//如下的代码是将页眉显示的中文显示我们自定义的中文。
                             switch (type) {
                             case "first": return "首页";
                             case "prev": return "上一页";
                             case "next": return "下一页";
                             case "last": return "末页";
                             case "page": return page;
                             }
                         },
                         
                         onPageClicked: function (event, originalEvent, type, page){//给每个页眉绑定一个事件，其实就是ajax请求，其中page变量为当前点击的页上的数字。
                                 $.ajax({
                                     url:'http://localhost:8085/biz/news/page',
                                     type:'POST',
                                     data:{'page':page,'count':7},
                                     dataType:'JSON',
                                     success:function (callback) {
                                            $('#newsDetail').empty();
                                            var newsDetail = "";
                                            var newsList = [];
                                            for(var i = 0;i < callback.data.records.length;i++) {
                                                newsList.push(callback.data.records[i]);
                                                newsDetail += '<tr class="gradeA"><td>'+callback.data.records[i].newsId+'</td><td style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;width50px">'+callback.data.records[i].newsTitle+'</td><td>'+callback.data.records[i].newsContent.substring(0,15)+"..."+'</td><td>'+callback.data.records[i].gmtModify+'</td><td>'+callback.data.records[i].gmtCreate+'</td><td><button class="btn btn-info detail" id='+i+'>详情</button>  <button class="btn btn-warning update" id='+i+'>修改</button>  <button class="btn btn-danger delete" id='+i+'>删除</button></td></tr>';
                                            }

                                            $('#newsDetail').html(newsDetail)

                                            //给分页返回的修改按钮添加事件
                                            vm.$nextTick().then(() => {
                                                  $('.update').on('click',function(){
                                                    vm.id = newsList[$(this).attr("id")].newsId;
                                                     axios
                                                            .get("http://localhost:8085/biz/newsClass/queryClassByNewsId",{
                                                                params : {
                                                                    "newsId" : vm.id
                                                                }
                                                            })
                                                            .then(response => {
                                                                vm.selectClass = "";
                                                                for(var i = 0;i < response.data.data.length;i++) {
                                                                 vm.selectClass += " "+response.data.data[i].name
                                                                }
                                                            })
                                                    $("#myModalLabel").text("修改");
                                                    $("#news_title").val(newsList[$(this).attr("id")].newsTitle);
                                                    // $("#news_content").val(newsList[$(this).attr("id")].newsContent);
                                                    myeditor.setData(newsList[$(this).attr("id")].newsContent);
                                                    $("#creator").val(newsList[$(this).attr("id")].gmtModify);
                                                    $('#myModal').modal();
                                                   
                                                 })

                                            })


                                            //给分页返回的详情按钮添加事件
                                            vm.$nextTick().then(() => {
                                                  $('.detail').on('click',function(){
                                                    vm.id = newsList[$(this).attr("id")].newsId;
                                                     axios
                                                            .get("http://localhost:8085/biz/newsClass/queryClassByNewsId",{
                                                                params : {
                                                                    "newsId" : vm.id
                                                                }
                                                            })
                                                            .then(response => {
                                                                vm.selectClass = "";
                                                                for(var i = 0;i < response.data.data.length;i++) {
                                                                 vm.selectClass += " "+response.data.data[i].name
                                                                }
                                                            })
                                                    $("#detailModalLabel").text("详情");
                                                    $("#detail_news_title").val(newsList[$(this).attr("id")].newsTitle);
                                                    // $("#news_content").val(newsList[$(this).attr("id")].newsContent);
                                                    detail_editor.setData(newsList[$(this).attr("id")].newsContent);
                                                    $("#detail_creator").val(newsList[$(this).attr("id")].gmtModify);
                                                    $('#detailModal').modal();
                                                 })

                                            })

                                            //给分页返回的删除按钮添加事件
                                            vm.$nextTick().then(() => {
                                                console.log(newsList[0])
                                                console.log($(this).attr("id"))
                                                  $('.delete').on('click',function(){
                                                        axios
                                                            .get("http://localhost:8085/biz/news/delete",{
                                                                params : {
                                                                    "newsId" :  newsList[$(this).attr("id")].newsId
                                                                }
                                                            })
                                                            .then(response => {
                                                                alert("success")
                                                                location.reload();
                                                            })
                                                     })

                                            })
                                         }
                                 })


                             }



                     });




                }
                    
                    )
                .catch(function (error){
                })
               // 下拉框添加剩余数据
                                    axios
                                    .get("http://localhost:8085/biz/class/queryAll")
                                    .then(response => {

                                        for (var i = 0; i < response.data.data.length; i++) {
                                            vm.nodeList += "<option value="+response.data.data[i].id+">"+response.data.data[i].name+"</option>"
                                        }
                                    })
            }
        })