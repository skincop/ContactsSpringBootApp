<#macro loginForm path isRegisterForm>

    <form action="${path}" method="post" >
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
        </div>

        <#if !isRegisterForm><a href="/registration">Add new user</a></#if>
        <button class="btn btn-primary" type="submit"><#if isRegisterForm>Create<#else>Sign In</#if></button>
    </form>
    <#if message??>
        <h3>${message}</h3>
    </#if>
</#macro>

<#macro logout>
    <form action="/logout" method="post">

        <input type="submit" value="Sign Out"/>
    </form>
</#macro>