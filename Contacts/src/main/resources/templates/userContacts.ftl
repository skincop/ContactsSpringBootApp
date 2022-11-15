<#import "parts/common.ftl" as c>
<#import "parts/contactsList.ftl" as cl>

<@c.page>
    <@cl.contactList>
        <input type="hidden" name="selectedUser" class="form-control" value="${userId?if_exists}">
    </@cl.contactList>
</@c.page>