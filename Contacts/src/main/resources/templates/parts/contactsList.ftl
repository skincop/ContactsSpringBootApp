<#import "pager.ftl" as p>
<#macro contactList path="/contacts/filter">
<#if !userId??>
     <#assign userId=0>
</#if>
    <h1>Contacts list</h1>
    <form method="get" action="${path}" class="form-inline">
        <input type="text" name="filter" class="form-control" value="${filter?if_exists}" placeholder="Search by name">
        <#nested>
        <button type="submit" class="btn btn-primary ml-2">Search</button>
    </form>
<#if contacts?has_content>

        <table class="table table-hover">
            <thead>
            <tr>
                <th scope="col">First Name</th>
                <th scope="col">Second Name</th>
                <th scope="col">Email</th>
                <th scope="col">Phone Number</th>
                <th scope="col"></th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <#list contacts.content as contact>
                <tr>
                    <td>${contact.firstName}</td>
                    <td>${contact.secondName}</td>
                    <td>${contact.email}</td>
                    <td>${contact.phoneNumber}</td>
                    <td><a class="btn btn-primary" role="button" href="/contacts/delete/${contact.id}?user=${userId}">delete</a></td>
                    <td><a class="btn btn-primary" role="button" href="/contacts/update/${contact.id}?user=${userId}">edit</a></td>
                    <td><a class="btn btn-primary" role="button" href="/contacts/send/${contact.id}">send</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    <@p.pager url contacts/>
    <#else>
    <H1>No Contacts</H1>
</#if>
</#macro>