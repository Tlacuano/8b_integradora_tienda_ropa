import Vue from 'vue'

import App from './App.vue'
import router from './router'
import store from './store/store'

import './assets/main.css'

// BootstrapVue
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'

Vue.use(BootstrapVue)
Vue.use(IconsPlugin)


// sweetalert2
import VueSweetalert2 from "vue-sweetalert2";
import 'sweetalert2/dist/sweetalert2.min.css';

Vue.use(VueSweetalert2);

// fontawesome
import { library } from "@fortawesome/fontawesome-svg-core";
import { fas } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";

library.add(fas);

Vue.component("font-awesome-icon", FontAwesomeIcon);



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

