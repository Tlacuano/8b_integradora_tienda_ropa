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
import {fab} from "@fortawesome/free-brands-svg-icons";
import {fas} from "@fortawesome/free-solid-svg-icons";
import {far} from "@fortawesome/free-regular-svg-icons";
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
library.add(fab);
library.add(fas);
library.add(far);
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
            name_max: () => "El nombre debe contener máximo 30 caracteres",
            image: () => 'El archivo debe ser una imagen',
            curp: () => 'Verifica el formato de tu CURP.',
            curp_length: () => 'La longitud debe ser 18 caracteres.',
            rfc: () => 'Verifica el formato de tu RFC.',
            rfc_length: () => 'La longitud debe ser 13 caracteres.',
            rfc_length_moral: () => 'La longitud debe ser 12 caracteres.',
            phone: () => 'El número de teléfono no es válido',
            product_name_max:()=>'El nombre del producto debe contener máximo 50 caracteres',
            product_name_min: () => 'El nombre del producto debe contener minimo 5 caracteres',
            description_min:()=>'La descripción debe contener minimo 20 caracteres',
            description_nax:()=> 'La descripción debe contener un máximo de 255 caracteres',
            card_number: () => 'El número de tarjeta debe contener 16 dígitos',
            negative_numbers: () => 'No se permiten números negativos',
            not_zero: () => 'No se permiten valores en cero',
            alpha:()=>'Este campo solo puede contener letras',
            minor_120: () => 'La edad no puede ser mayor a 120 años',
            cvv: () => 'El CVV debe contener 3 dígitos',
            rejection_reason_length: () => 'La razón de rechazo no debe exceder los 255 caracteres',
            only_enters: () => 'Solo se permiten números enteros',
            no_e:()=> 'No se permiten expresiones cientificas',
        }
    }
};

//for rejection reason length
Validator.extend('rejection_reason_length', {
    validate: value => {
        return value.length <= 255;
    }
});

Validator.extend('only_enters', {
    validate: value => {
        const onlyEntersRegex = /^\d+$/;
        return onlyEntersRegex.test(value);
    }
});

Validator.extend('negative_numbers', {
    validate: value => {
        return value >= 0;
    }
});
Validator.extend('alpha', {
    validate: value => {
        const alphaRegex = /^[a-zA-Z]+$/;
        return alphaRegex.test(value);
    }
});
Validator.extend('not_zero', {
    validate: value => {
        return value !== 0;
    }
})


//for card number
Validator.extend('card_number', {
    validate: value => {
        const cardNumberRegex = /^\d{16}$/;
        return cardNumberRegex.test(value);
    }
});

//for cvv
Validator.extend('cvv', {
    validate: value => {
        const cvvRegex = /^\d{3}$/;
        return cvvRegex.test(value);
    }
});

//for curp length
Validator.extend('curp_length', {
    validate: value => {
        return value.length === 18;
    }
});

//for curp
Validator.extend('curp', {
    validate: value => {
        const curpRegex = /^[A-Z]{4}\d{6}[HM][A-Z]{5}[A-Z\d]\d$/;
        return curpRegex.test(value);
    }
});

//for rfc length fisica
Validator.extend('rfc_length', {
    validate: value => {
        return value.length === 13;
    }
});

//for rfc length moral
Validator.extend('rfc_length_moral', {
    validate: value => {
        return value.length === 12;
    }
});

//for rfc fisica
Validator.extend('rfc', {
    validate: value => {
        const rfcRegex = /^[A-Z]{4}\d{6}[A-Z\d]{3}$/;
        return rfcRegex.test(value);
    }
});

//for rfc moral
Validator.extend('rfc_moral', {
    validate: value => {
        const rfcRegex = /^[A-Z]{3}\d{6}[A-Z\d]{3}$/;
        return rfcRegex.test(value);
    }
});

//for phone number
Validator.extend('phone', {
    validate: value => {
        const phoneRegex = /^\d{10}$/;
        return phoneRegex.test(value);
    }
});

//for subcategory name lenght
Validator.extend("name_max", {
    validate: value => {
        return value.length <= 30;
    }
})
//for productName lenght
Validator.extend("product_name_max",{
    validate: value => {
        return value.length <= 50;
    }
})
Validator.extend("product_name_min",{
    validate: value => {
        return value.length >= 5;
    }
})

//for image size
Validator.extend('image_size', {
    validate: value => {
        return value.size < 2000000;
    }
});

// for password strength
Validator.extend('password_strength', {
    validate: value => {
        const strongRegex = new RegExp("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.{8,})");
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

Validator.extend('minor_120', {
    validate: value => {
        const today = new Date();
        const birthDate = new Date(value);
        let age = today.getFullYear() - birthDate.getFullYear();
        const m = today.getMonth() - birthDate.getMonth();

        if (m < 0 || (m === 0 && today.getDate() < birthDate.getDate())) {
            age--;
        }

        return age < 120;
    }
});

//validate privacy policy
Validator.extend('privacy_policy', {
    validate: value => {
        return value;
    }
});

Validator.extend("description_max", {
    validate: value => {
        return value.length <= 255;
    }
})

Validator.extend("description_min", {
    validate: value => {
        return value.length >= 20;
    }
})
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

