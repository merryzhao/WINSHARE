seajs.use(["jQuery", "jQueryUI"], function($, uiPlugin) {
    var $ = $.sub();
    uiPlugin($);
    var password = {
        info : {
            passwordInfo : '密码：6-16位字符，可由英文、数字及"_"、"-"组成',
            repasswordInfo : "两次输入密码不一致",
            passwordReg : /^[A-Za-z0-9_-]{6,16}$/
        },
        defOpt : {
            action : "http://www.winxuan.com/customer/updatePassword"
        },
        init : function() {
            this.passwordForm = $("#updatePasswordForm");
            this.newPassword = this.passwordForm.find("input[name='newPassword']");
            this.rePassword = this.passwordForm.find("input[name='newPasswordConfirm']");
            this.bind();
        },
        bind : function() {
            var obj = this;          
            this.passwordForm.submit(function(e) {
                if (!obj.validate()) {
                    // cancel form
                    e.preventDefault();
                    e.stopPropagation();
                    return false;
                }
            });
        },
        showMessage : function(msg) {
            alert(msg);
        },

        validate : function() {
            var newPasswordVal = this.newPassword.val(), 
            rePasswordVal = this.rePassword.val(), 
            info = this.info;
            this.newPassword.next('span').remove();
            this.rePassword.next('span').remove();
            if (!(info.passwordReg.test(newPasswordVal))) {
                this.newPassword.after('<span class="red">' + info.passwordInfo + '</span>');
                return false;
            }
            if ((rePasswordVal != newPasswordVal) || (!rePasswordVal)) {
                this.rePassword.after('<span class="red">' + info.repasswordInfo + '</span>')
                return false;
            }
                return true;

        }
    };
    password.init();
});
