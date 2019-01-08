function getPermissionsOfRole(roleId) {
    fetch("http://localhost:8080/role/getPermissionsOfRole?roleId=" + roleId.toString()).then(res => {
        return res.json();
    }).then(json => {
        console.log(json);
        let psUl = document.getElementById("role-" + roleId);
        for (let pm of json.wrapper) {
            let li = document.createElement("li");
            let text = document.createTextNode(pm.name);
            let text2 = document.createTextNode(" ；描述："pm.description);
            li.appendChild(text);
            li.appendChild(text2);
            psUl.appendChild(li);
        }
        return json;
    }).catch(err => {
        console.log(err);
    });
}
getPermissionsOfRole(1)