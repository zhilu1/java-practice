function getAllRoles() {
    fetch("http://localhost:8080/authority/getAllRoles").then(res => {
        console.log(res);
    })
}
