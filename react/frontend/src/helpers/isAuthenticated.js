export default function isAuthenticated(what_to_render)  {

    let user = localStorage.getItem('currentUser');

    if(user && user.token){
        return (what_to_render);
    }
    return "";
}