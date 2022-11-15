<#import "parts/common.ftl" as c>
<#import "parts/loginForm.ftl" as l>

<@c.page>
    <form method="post" action="/contacts/send" class="form-inline">
        <input type="email" name="email"  placeholder="someemail@gmail.com">
        <input type="hidden" name="contact" value="${contactId?if_exists}" />
        <button type="submit" class="btn btn-primary ml-2">Send</button>
    </form>
</@c.page>