<#import "loginForm.ftl" as l>
<#assign  security=JspTaglibs["http://www.springframework.org/security/tags"] />

<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <a class="navbar-brand" href="/">Contacts</a>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto">
            <li class="nav-item">
                <a class="nav-link" href="/">Home</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="/contacts">Contacts</a>
            </li>
            <@security.authorize access="hasAuthority('ROLE_ADMIN')">
                <li class="nav-item">
                    <a class="nav-link" href="/user">User list</a>
                </li>
            </@security.authorize>
        </ul>
        <@security.authorize access="isAuthenticated()">
            <div class="navbar-text mr-3"><@security.authentication property="principal.username" /></div>
        </@security.authorize>
        <@l.logout />
    </div>
</nav>
