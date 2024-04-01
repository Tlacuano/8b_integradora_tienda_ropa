import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/store'
import './assets/main.css'
import {BootstrapVue, IconsPlugin, ToastPlugin} from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VueSweetalert2 from "vue-sweetalert2";
import 'sweetalert2/dist/sweetalert2.min.css';
import {library} from "@fortawesome/fontawesome-svg-core";
import {fas} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/vue-fontawesome";
import VeeValidate, {Validator} from 'vee-validate'
import es from 'vee-validate/dist/locale/es';

// BootstrapVue
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)
Vue.use(ToastPlugin)

// sweetalert2
Vue.use(VueSweetalert2);

// fontawesome
library.add(fas);
Vue.component("font-awesome-icon", FontAwesomeIcon);

// veeValidate
const dictionary = {
    es: {
        messages: {
            required: () => 'Este campo es obligatorio',
            email: () => 'El correo electrónico no es válido',
            password_strength: () => 'La contraseña no es válida',
            confirmed_password: () => 'Las contraseñas no coinciden',
            alpha_spaces: () => 'Este campo solo puede contener letras y espacios',
            phone_number: () => 'El número de teléfono no es válido',
            over_18: () => 'Debes ser mayor de 18 años',
            privacy_policy: () => 'Debes aceptar la política de privacidad',
            image_size: () => 'La imagen no debe pesar más de 2MB',
            digits: () => 'Debe contener exactamente 5 dígitos',
            min: () => 'Debe contener al menos 5 caracteres',
            max: () => 'Debe contener máximo 100 caracteres',
        }
    }
};

//for image size
Validator.extend('image_size', {
    validate: value => {
        return value.size < 2000000;
    }
});

// for password strength
Validator.extend('password_strength', {
    validate: value => {
        var strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})");
        return strongRegex.test(value);
    }
});

// for confirmed password
const paramNames = ['targetValue'];

Validator.extend('confirmed_password', {
    validate: (value, {targetValue}) => {
        return value === targetValue;
    }
}, {
    paramNames, hasTarget: true
});

Validator.extend('phone_number', {
    validate: value => {
        const phoneRegex = /^\d{10}$/;
        return phoneRegex.test(value);
    }
});

Validator.extend('over_18', {
    validate: value => {
        const today = new Date();
        const birthDate = new Date(value);
        let age = today.getFullYear() - birthDate.getFullYear();
        const m = today.getMonth() - birthDate.getMonth();

        if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }

        return age >= 18;
    }
});

//validate privacy policy
Validator.extend('privacy_policy', {
    validate: value => {
        return value;
    }
});

Validator.localize('es', es);
Validator.localize({es: {messages: {...es.messages, ...dictionary.es.messages}}});
Vue.use(VeeValidate, {
    locale: 'es'
});

window.addEventListener('beforeunload', () => {
    store.dispatch('prepareForReload');
});

// router title
router.beforeEach((to, from, next) => {
    document.title = to.meta.title || 'K&I';
    next();
});

new Vue({
    router, store, render: (h) => h(App), created() {
        this.$store.dispatch('restoreAuthData');
    }
}).$mount('#app')

