import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/store'
import './assets/main.css'
import { BootstrapVue, IconsPlugin, ToastPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VueSweetalert2 from "vue-sweetalert2";
import 'sweetalert2/dist/sweetalert2.min.css';
import { library } from "@fortawesome/fontawesome-svg-core";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import VeeValidate  from 'vee-validate'
import { Validator } from 'vee-validate';
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

    }
  }
};

Validator.localize('es', es);
Validator.localize({ es: { messages: { ...es.messages, ...dictionary.es.messages } } });
Vue.use(VeeValidate, {
  locale: 'es'
});





window.addEventListener('beforeunload', () => {
  store.dispatch('prepareForReload');
});

new Vue({
  router,
  store,
  render: (h) => h(App),
  created() {
    this.$store.dispatch('restoreAuthData');
  }
}).$mount('#app')

