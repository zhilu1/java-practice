function getPermissionsOfRole(roleId) {
    return fetch("http://localhost:8080/role/getPermissionsOfRole?roleId=" + roleId.toString())
        .then(res => {
            return res.json();
        }).catch(err => {
            console.log(err);
            reject(err);
        });
}

function createPermissionsElements(roleId ,json) {
    let psUl = document.getElementById("role-" + roleId);
    for (let pm of json.wrapper) {
        let li = document.createElement("li");
        let text = document.createTextNode(pm.name);
        let text2 = document.createTextNode(" ；描述：" + pm.description);
        li.appendChild(text);
        li.appendChild(text2);
        psUl.appendChild(li);
    }
}

function generatePermissions(roleId) {
    getPermissionsOfRole(roleId).then(json => {
        createPermissionsElements(roleId, json);
    })
}

function getAllPermissions() {
    return fetch("http://localhost:8080/role/getAllPermissions").then(res => {
        return res.json();
    }).catch(err => {
        console.log(err);
    });
}

function checkContainsId(list, item){
    for (let i = 0; i < list.length; i++) {
        if (list[i].id == item.id) {
            return true
        }
    }
    return false;
}
function changeRole(role) {
    let pms = document.getElementById("permissions");
    let name = document.getElementById("name");
    let searchParams = new URLSearchParams();
    searchParams.set('roleId', role.id);
    searchParams.set('name', name.value);
    for (let i = 0; i < pms.length; i++) {
        let permission = pms.options[i];
        if (permission.selected == true){
            searchParams.append('permissions', parseInt(permission.value));
        }
    }

    fetch("http://localhost:8080/role/changeRole",{
        method: 'POST',
        body: searchParams
    }).then(res => {
        return res.json();

    }).then(json =>{
        console.log(json);
        if(json.errMsg != ""){
            let errDiv = document.getElementById("errorMsg");
            errDiv.innerText = json.errMsg;
            errDiv.style.display = "block";
        }
        else{
            window.location = "http://localhost:8080/role/getAllRoles";
        }
    }).catch(err => {
        console.log(err);
    });

}
function registerRole() {
    let pms = document.getElementById("permissions");
    let name = document.getElementById("name");
    let searchParams = new URLSearchParams();
    searchParams.set('name', name.value);
    for (let i = 0; i < pms.length; i++) {
        let permission = pms.options[i];
        if (permission.selected){
            searchParams.append('permissions', parseInt(permission.value));
        }
    }
    fetch("http://localhost:8080/role/registerRole",{
        method: 'POST',
        body: searchParams
    }).then(res => {
        return res.json();

    }).then(json =>{
        if(json.errMsg != ""){
            let errDiv = document.getElementById("errorMsg");
            // let msg = document.createTextNode(json.errMsg);
            // errDiv.appendChild(msg);
            errDiv.innerText = json.errMsg;
            errDiv.style.display = "block";
        }
        else{
            window.location = "http://localhost:8080/role/getAllRoles";
        }
    }).catch(err => {
        console.log(err);
    });

}