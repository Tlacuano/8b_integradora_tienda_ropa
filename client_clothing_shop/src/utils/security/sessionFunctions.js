export function isLoggedInS() {
    return !!localStorage.getItem("token");
}

export function getRoleS() {
    return localStorage.getItem("role");
}

export function logout(){
    localStorage.removeItem("token");
    window.location.reload();
}