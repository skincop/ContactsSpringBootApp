<#macro contactForm>

    <a class="btn btn-primary" data-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
        Add contact
    </a>
<div class="collapse <#if wrongContact??>show</#if>" id="collapseExample">
    <form method="post" path="/">
        <input type="text" name="firstName" placeholder="Введите имя" class="form-control ${(firstNameError??)?string('is-invalid', '')}"
               value="<#if wrongContact??>${wrongContact.firstName}</#if>"/>
                <#if firstNameError??>
                    <div class="invalid-feedback">
                        ${firstNameError}
                    </div>
                </#if>
        <input type="text" name="secondName" placeholder="Введите фамилию" class="form-control ${(secondNameError??)?string('is-invalid', '')}"
               value="<#if wrongContact??>${wrongContact.secondName}</#if>">
                <#if secondNameError??>
                    <div class="invalid-feedback">
                        ${secondNameError}
                    </div>
                </#if>
        <input type="email" name="email" placeholder="Введите почту" class="form-control ${(emailError??)?string('is-invalid', '')}"
               value="<#if wrongContact??>${wrongContact.email}</#if>">
                <#if emailError??>
                    <div class="invalid-feedback">
                        ${emailError}
                    </div>
                </#if>
        <input type="text" name="phoneNumber" placeholder="Введите номер телефона" class="form-control ${(phoneNumberError??)?string('is-invalid', '')}"
               value="<#if wrongContact??>${wrongContact.phoneNumber}</#if>">
                <#if phoneNumberError??>
                    <div class="invalid-feedback">
                        ${phoneNumberError}
                    </div>
                </#if>

        <button type="submit" class="btn btn-primary ml-2">Add</button>
    </form>
</div>
</#macro>