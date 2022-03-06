let appRegister = new Vue({
  el: "#appRegister",
  data: {
    firstName: "",
    lastName: "",
    email: "",
    password: "",
  },

  methods: {

    iniciarSesion() {
      axios
        .post(
          "/api/login",
          "email=" + this.email + "&password=" + this.password,
          {
            headers: { "content-type": "application/x-www-form-urlencoded" },
          }
        )

        .then((response) => {
          console.log("signed in!!!");
          alert("Tus datos son correctos!");
          window.location.href = "/web/accounts.html";
        })
        .catch(function (error) {
          if (error.response) {
            // The request was made and the server responded with a status code
            // that falls out of the range of 2xx
            // Con el catch agarro el error del response, eso quiere decir que el email o la contraseña son invalidas, o no encontró el usuario o la contraseña está mal, y lo muestro por pantalla
            Swal.fire({
              icon: "error",
              text: "Email o contraseña invalido, vuelva a intentar!",
              showConfirmButton: false,
            });
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            // The request was made but no response was received
            // error.request is an instance of XMLHttpRequest in the browser and an instance of
            console.log(error.request);
          } else {
            // Something happened in setting up the request that triggered an Error
            console.log("Error", error.message);
          }
          console.log(error.config);
        });
    },

    registracion() {
      axios.post('/api/clients',
     // "firstName=pedro2&lastName=rodriguez&email=pedro@mindhub.com&password=pedro",
     "firstName=" + this.firstName + "&lastName=" + this.lastName + "&email=" + this.email + "&password=" + this.password,
      {headers:{'content-type':'application/x-www-form-urlencoded'
    }})
      .then(response => console.log('registered'))
      alert("Te registraste perfecto!")
      this.iniciarSesion()
        

    },

    cerrarSesion() {
      axios
        .post("/api/logout",
         "email=" + this.email + "&password=" + this.password, {
          headers: { "content-type": "application/x-www-form-urlencoded" },
        })
        .then((response) => console.log("signed out!!!"));
        alert("Cerraste tu sesión. Hasta la vista!");
          window.location.href = "/web/index.html";
    },
  },
});


