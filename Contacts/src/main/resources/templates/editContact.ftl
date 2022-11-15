<#import "parts/common.ftl" as c>
<#import "parts/loginForm.ftl" as l>
<#import "parts/contactForm.ftl" as cf>

<@c.page>
    <form method="post" action="/contacts/update/${contact.id}">
        <input type="text" name="firstName" value="${contact.firstName}" class="form-control ${(firstNameError??)?string('is-invalid', '')}"/>
        <#if firstNameError??>
            <div class="invalid-feedback">
                ${firstNameError}
            </div>
        </#if>
        <input type="text" name="secondName" value="${contact.secondName}" class="form-control ${(secondNameError??)?string('is-invalid', '')}"/>
        <#if secondNameError??>
            <div class="invalid-feedback">
                ${secondNameError}
            </div>
        </#if>
        <input type="email" name="email" value="${contact.email}" class="form-control ${(emailError??)?string('is-invalid', '')}"/>
        <#if emailError??>
            <div class="invalid-feedback">
                ${emailError}
            </div>
        </#if>
        <input type="tel" name="phoneNumber" value="${contact.phoneNumber}" class="form-control ${(phoneNumberError??)?string('is-invalid', '')}"/>
        <#if phoneNumberError??>
            <div class="invalid-feedback">
                ${phoneNumberError}
            </div>
        </#if>
        <input type="hidden" name="user" value="${userId?if_exists}" />
        <input type="submit" name="send">
    </form>
</@c.page>