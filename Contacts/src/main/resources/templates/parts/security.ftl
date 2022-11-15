<#assign  security=JspTaglibs["http://www.springframework.org/security/tags"] />


<#if known>
    <#assign
        user =
    user = Session.SPRING_SECURITY_CONTEXT.authentication.principal
    name = user.getUsername()
    isAdmin = user.isAdmin()
    >
<#else>
    <#assign
    name = "unknown"
    isAdmin = false
    >
</#if>