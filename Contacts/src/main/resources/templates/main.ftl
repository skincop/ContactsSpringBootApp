<#import "parts/common.ftl" as c>
<#import "parts/loginForm.ftl" as l>
<#assign  security=JspTaglibs["http://www.springframework.org/security/tags"] />

<@c.page>
    <@security.authorize access="!isAuthenticated()">
        <a href="/login">Login</a>
    </@security.authorize>
    <@security.authorize access="isAuthenticated()">
        <@l.logout />
    </@security.authorize>
    <h1>Main</h1>
</@c.page>