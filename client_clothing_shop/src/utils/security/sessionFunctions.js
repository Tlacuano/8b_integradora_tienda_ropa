export function isLoggedIn() {
    return !!localStorage.getItem("token");
}

export function getRole() {
    //implementar servicio para obtener el rol del usuario
    return "rol";
}

export function logout(){
    localStorage.removeItem("token");
    window.location.reload();
}