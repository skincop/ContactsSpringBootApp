<#import "parts/common.ftl" as c>
<#import "parts/loginForm.ftl" as l>

<@c.page>
    <div>
        <@l.logout />
    </div>
    <h1>${error}</h1>
</@c.page>