import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        token: '',
        email: '',
        role: '',
        hasMultipleRoles: false
    },
    mutations:{
        setAuthData(state,{token, email, role, hasMultipleRoles}){
            state.token = token;
            state.email = email;
            state.role = role;
            state.hasMultipleRoles = hasMultipleRoles;
        },
        clearAuthData(state){
            state.token = '';
            state.email = '';
            state.role = '';
            state.hasMultipleRoles = false;
        },
        switchUserRole(state, { newRole, newToken }){
            state.role = newRole;
            state.token = newToken;
        }
    },
    actions:{
        restoreAuthData({ commit }) {
            let token = localStorage.getItem('token');
            let email = localStorage.getItem('email');
            let role = localStorage.getItem('role');
            let hasMultipleRoles = localStorage.getItem('hasMultipleRoles') === 'true';

            if (token) {
                commit('setAuthData', { token, email, role, hasMultipleRoles });
                localStorage.removeItem('token');
                localStorage.removeItem('email');
                localStorage.removeItem('role');
                localStorage.removeItem('hasMultipleRoles');
            }
        },
        prepareForReload({ state }) {
            localStorage.setItem('token', state.token);
            localStorage.setItem('email', state.email);
            localStorage.setItem('role', state.role);
            localStorage.setItem('hasMultipleRoles', state.hasMultipleRoles.toString());
        },
        login({ commit }, payload) {
            commit('setAuthData', payload);
        },
        logout({ commit }) {
            commit('clearAuthData');
            window.location.reload();
        },
        switchRole({ commit, state }, { newRole, newToken }) {
            commit('switchUserRole', { newRole, newToken });
            window.location.reload();
        }
    },
    getters:{
        isLoggedIn: state => state.token.length > 0,
        getToken: state => state.token,
        getEmail: state => state.email,
        getRole: state => state.role,
        hasMultipleRoles: state => state.hasMultipleRoles
    }


});