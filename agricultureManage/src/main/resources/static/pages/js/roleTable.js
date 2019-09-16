var vm = new Vue({
            el: "#roleTable",
            data: {
                roleList: [],
                roleDetail: "",
                permission: "",
                nodeList: "",
                id : "",
                selectClass : ""
            },
            methods: {
                ceshi:function(){
                    alert(vm.roleDetail)
                },
                change : function(){
                    let param = new URLSearchParams()
                param.append("roleId" ,this.id)
                param.append("roleName" , $("#update_role_name").val())
                param.append("roleDesc" , $("#update_description").val())
                if($("#update_allNode").val() != null){
                    param.append('permission' , $("#update_allNode").val())
                }
                axios
                .post("http://localhost:8085/biz/role/update",param)
                .then(response => {
                    if(response.data.code == '200'){
                        alert("修改成功");
                        location.reload();
                    }else{
                        alert(response.data.msg)
                    }
                })
                },
                showAdd : function(){
                    $('#addModal').modal();
                },
                upload:function(){
                let param = new URLSearchParams()
                param.append('roleDesc', $("#description").val()),
                param.append("roleName" , $("#role_name").val()),
                param.append("roleCode" , $("#role_code").val())
                if($("#add_allNode").val() != null){
                    param.append('permission' , $("#add_allNode").val())

                    axios
                    .post("http://localhost:8085/biz/role/add",param
                    )
                    .then(response => {
                        if(response.data.code == '200'){
                            alert("添加成功")
                            location.reload();
                        }else if(response.data.code == '400'){
                            alert(response.data.msg)
                        }
                    })
                }else{
                    alert("角色权限不能为空")
                }
                
                
            }
            },
            mounted(){
                axios
                .get('http://localhost:8085/biz/role/page',{
                    params: {
                        "page" : 1,
                        "count" : 7
                    }
                })
                .then(response => {
                    window.total = response.data.data.pages
                    for(var i = 0;i < response.data.data.records.length;i++) {
                        vm.roleList.push(response.data.data.records[i]),
                        vm.roleDetail += '<tr class="gradeA" ><td>'+this.roleList[i].roleId+'</td><td style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;width50px">'+this.roleList[i].roleName+'</td><td>'+this.roleList[i].roleCode+"..."+'</td><td>'+this.roleList[i].roleDesc+'</td><td>'+this.roleList[i].createTime+'</td><td><button class="btn btn-info detail" id='+i+'>详情</button>  <button class="btn btn-warning update" id='+i+'>修改</button>   <button class="btn btn-danger delete" id='+i+'>删除</button></td></tr>';
                    }




                    //给修改按钮添加事件
                    this.$nextTick().then(() => {
                          $('.update').on('click',function(){
                            vm.id = vm.roleList[$(this).attr("id")].roleId;
                            axios
                                .get("http://localhost:8085/biz/roleMenu/queryMenuByRoleId",{
                                    params : {
                                        "roleId" : vm.id
                                    }
                                })
                                .then(response => {
                                    vm.selectClass = "";
                                    for(var i = 0;i < response.data.data.length;i++) {
                                        console.log(response.data.data[i]);
                                        vm.selectClass += " "+response.data.data[i].name
                                    }
                                })
                           $("#myModalLabel").text("修改");
                            $("#update_role_name").val(vm.roleList[$(this).attr("id")].roleName);
                            // $("#role_content").val(vm.roleList[$(this).attr("id")].roleContent);
                            $("#update_role_code").val(vm.roleList[$(this).attr("id")].roleCode);
                            $("#update_description").val(vm.roleList[$(this).attr("id")].roleDesc);
                            $('#myModal').modal();
                            
                         })

                    })

                    //给详情按钮添加事件
                    this.$nextTick().then(() => {
                          $('.detail').on('click',function(){
                            vm.id = vm.roleList[$(this).attr("id")].roleId;
                            axios
                                .get("http://localhost:8085/biz/roleMenu/queryMenuByRoleId",{
                                    params : {
                                        "roleId" : vm.id
                                    }
                                })
                                .then(response => {
                                    vm.selectClass = "";
                                    for(var i = 0;i < response.data.data.length;i++) {
                                        vm.selectClass += " "+response.data.data[i].name
                                    }
                                })
                            $("#detailModalLabel").text("详情");
                            $("#detail_role_name").val(vm.roleList[$(this).attr("id")].roleName);
                            // $("#role_content").val(vm.roleList[$(this).attr("id")].roleContent);
                            $("#detail_create_time").val(vm.roleList[$(this).attr("id")].createTime);
                            $("#detail_role_code").val(vm.roleList[$(this).attr("id")].roleCode);
                            $("#detail_description").val(vm.roleList[$(this).attr("id")].roleDesc);
                            $('#detailModal').modal();
                            vm.id = vm.roleList[$(this).attr("id")].roleId;
                         })

                    })

                    //给删除按钮添加事件
                    this.$nextTick().then(() => {
                          $('.delete').on('click',function(){
                            axios
                                .get("http://localhost:8085/biz/role/delete",{
                                    params : {
                                        "roleId" :  vm.roleList[$(this).attr("id")].roleId
                                    }
                                })
                                .then(response => {
                                    if(response.data.code == '200'){
                                        alert("删除成功");
                                        location.reload();
                                    }else{
                                        alert(response.data.msg)
                                    }
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
                                     url:'http://localhost:8085/biz/role/page',
                                     type:'POST',
                                     data:{'page':page,'count':7},
                                     dataType:'JSON',
                                     success:function (callback) {
                                            $('#roleDetail').empty();
                                            var roleDetail = "";
                                            var roleList = [];
                                            for(var i = 0;i < callback.data.records.length;i++) {
                                                roleList.push(callback.data.records[i]);
                                                roleDetail += '<tr class="gradeA"><td>'+callback.data.records[i].roleId+'</td><td style="white-space:nowrap;overflow:hidden;text-overflow: ellipsis;width50px">'+callback.data.records[i].roleName+'</td><td>'+callback.data.records[i].roleCode+"..."+'</td><td>'+callback.data.records[i].roleDesc+'</td><td>'+callback.data.records[i].createTime+'</td><td><button class="btn btn-info detail" id='+i+'>详情</button>  <button class="btn btn-warning update" id='+i+'>修改</button>  <button class="btn btn-danger delete" id='+i+'>删除</button></td></tr>';
                                            }

                                            $('#roleDetail').html(roleDetail)

                                            //给分页返回的修改按钮添加事件
                                            vm.$nextTick().then(() => {
                                                  $('.update').on('click',function(){
                                                    vm.id = roleList[$(this).attr("id")].roleId;
                                                     axios
                                                            .get("http://localhost:8085/biz/roleMenu/queryMenuByRoleId",{
                                                                params : {
                                                                    "roleId" : vm.id
                                                                }
                                                            })
                                                            .then(response => {
                                                                vm.selectClass = "";
                                                                for(var i = 0;i < response.data.data.length;i++) {
                                                                 vm.selectClass += " "+response.data.data[i].name
                                                                }
                                                            })
                                                    $("#myModalLabel").text("修改");
                                                    $("#update_role_name").val(roleList[$(this).attr("id")].roleName);
                                                    // $("#role_content").val(vm.roleList[$(this).attr("id")].roleContent);
                                                    $("#update_role_code").val(roleList[$(this).attr("id")].roleCode);
                                                    $("#update_description").val(roleList[$(this).attr("id")].roleDesc);
                                                    $('#myModal').modal();
                                                   
                                                 })

                                            })


                                            //给分页返回的详情按钮添加事件
                                            vm.$nextTick().then(() => {
                                                  $('.detail').on('click',function(){
                                                    vm.id = roleList[$(this).attr("id")].roleId;
                                                     axios
                                                            .get("http://localhost:8085/biz/roleMenu/queryMenuByRoleId",{
                                                                params : {
                                                                    "roleId" : vm.id
                                                                }
                                                            })
                                                            .then(response => {
                                                                vm.selectClass = "";
                                                                for(var i = 0;i < response.data.data.length;i++) {
                                                                 vm.selectClass += " "+response.data.data[i].name
                                                                }
                                                            })

                                                    $("#detailModalLabel").text("详情");
                                                    $("#detail_role_name").val(roleList[$(this).attr("id")].roleName);
                                                    // $("#role_content").val(vm.roleList[$(this).attr("id")].roleContent);
                                                    $("#detail_create_time").val(roleList[$(this).attr("id")].createTime);
                                                    $("#detail_role_code").val(roleList[$(this).attr("id")].roleCode);
                                                    $("#detail_description").val(roleList[$(this).attr("id")].roleDesc);
                                                    $('#detailModal').modal();
                                                 })

                                            })

                                            //给分页返回的删除按钮添加事件
                                            vm.$nextTick().then(() => {
                                                  $('.delete').on('click',function(){
                                                        axios
                                                            .get("http://localhost:8085/biz/role/delete",{
                                                                params : {
                                                                    "roleId" :  roleList[$(this).attr("id")].roleId
                                                                }
                                                            })
                                                            .then(response => {
                                                                if(response.data.code == '200'){
                                                                    alert("删除成功");
                                                                    location.reload();
                                                                }else{
                                                                    alert(response.data.msg)
                                                                }
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
                                    .get("http://localhost:8085/biz/menu/queryAll")
                                    .then(response => {

                                        for (var i = 0; i < response.data.data.length; i++) {
                                            vm.nodeList += "<option value="+response.data.data[i].menuId+">"+response.data.data[i].name+"</option>"
                                        }
                                        //要以编程方式更新JavaScript的选择，首先操作选择，然后使用refresh方法更新UI以匹配新状态。 在删除或添加选项时，或通过JavaScript禁用/启用选择时，这是必需的。
                                       $('.selectpicker').selectpicker('refresh');
                                       //render方法强制重新渲染引导程序 - 选择ui,如果当您编程时更改任何相关值而影响元素布局，这将非常有用。
                                       $('.selectpicker').selectpicker('render');
                                    })
            }
        })