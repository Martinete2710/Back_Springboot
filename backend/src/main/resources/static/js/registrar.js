// Call the dataTables jQuery plugin
$(document).ready(function() {
//on ready
});

async function registrarUsuario() {
  let datos = {};
  datos.nombre = document.getElementById('txtNombre').value;
  datos.apellido = document.getElementById('txtApellido').value;
  datos.email = document.getElementById('txtEmail').value;
  datos.password = document.getElementById('txtPassword').value;

  let repetirPassword = document.getElementById('txtRepetirPassword').value;

  if (repetirPassword !== datos.password) {
    alert('Las contraseñas son diferentes');
    return;
  }

  const request = await fetch("api/usuarios", {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos)
  });
alert(' la cuenta se creo correctamente por favor ingresa sesion')
window.location.href='login.html'
}