// Call the dataTables jQuery plugin
$(document).ready(function() {
//on ready
});

async function iniciarSesion() {
  let datos = {};
  datos.email = document.getElementById('txtEmail').value;
  datos.password = document.getElementById('txtPassword').value;

  try {
    const request = await fetch("api/login", {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(datos)
    });
const respuesta= await request.text();
    if (respuesta != 'FAIL') {
    alert('el ingreso fue correcto')
    localStorage.token= respuesta;
    localStorage.email=  datos.email;
    window.location.href='usuarios.html'

      // Realizar acciones en caso de inicio de sesión exitoso
    } else {
      // Manejar respuesta de error del servidor
      alert("Error al iniciar sesión");
    }
  } catch (error) {
    // Manejar errores de red o excepciones
    console.error("Error de red:", error);
  }
}