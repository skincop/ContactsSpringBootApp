<#import "parts/common.ftl" as c>

<@c.page>
    <#if users?has_content>
        <h1>List of users</h1>
        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Role</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <#list users as user>
                <tr>
                    <td>${user.username}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a class="btn btn-primary" role="button" href="/user/${user.id}">edit</a></td>
                    <td><a class="btn btn-primary" role="button" href="/user/info/${user.id}">show</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <H1>No Users</H1>
    </#if>
</@c.page>