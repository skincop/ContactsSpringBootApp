<#import "parts/common.ftl" as c>
<#import "parts/loginForm.ftl" as l>

<@c.page>
    Login page
<form action="/auth" method="post" enctype="multipart/form-data">
    <div>
        <label> User Name :
            <input type="text" name="username" class="form-control ${(usernameError??)?string('is-invalid', '')}"
                   value="<#if user??>${user.username}</#if>"/>
            <#if usernameError??>
                <div class="invalid-feedback">
                    ${usernameError}
                </div>
            </#if> </label>
    </div>
    <div>
        <label> Password:
            <input type="password" name="password" class="form-control ${(passwordError??)?string('is-invalid', '')}"
                   value="<#if user??>${user.password}</#if>"/>
            <#if passwordError??>
                <div class="invalid-feedback">
                    ${passwordError}
                </div>
            </#if> </label>
        <button class="btn btn-primary" type="submit">Sign In</button>
    </div>
</@c.page>