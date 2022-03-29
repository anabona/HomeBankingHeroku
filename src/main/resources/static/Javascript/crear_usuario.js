let crearUsuario = new Vue({
  el: '#crearUsuario',
  data: {
      name: "",
      lastName: "",
      email: "",
      password: "",


  },

  methods: {
      crearNuevoUsuario() {
          if (this.name == "" || this.lastName == "" || this.email == "" || this.password == "") {
              Swal.fire({
                  icon: 'error',
                  text: 'Falta completar uno o más campos!',
                  showConfirmButton: false
              });
          }
          else {

              axios.post('/api/clients', `firstName=${this.name}&lastName=${this.lastName}&email=${this.email}&password=${this.password}`, { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
                  .then(response => {
                      console.log('registered');
                      Swal.fire({
                          icon: 'success',
                          text: 'Usuario creado con exito!',
                          showConfirmButton: false
                      });
                      console.log("crea cuenta");
                      this.iniciarSesion();


                  })
                  .catch(function (error) {
                      if (error.response) {
                          console.log("ENTRA AL ERROR");
                          Swal.fire({
                              icon: 'error',
                              text: 'El email ya se encuentra registrado, pruebe con otra dirección de correo!',
                              showConfirmButton: false
                          });
                          console.log(error.response.data);
                          console.log(error.response.status);
                          console.log(error.response.headers);
                      } else if (error.request) {
                          console.log(error.request);
                      } else {
                          console.log('Error', error.message);
                      }
                      console.log(error.config);
                  });

          }

      },
      iniciarSesion() {
          axios.post('/api/login', "email=" + this.email + "&" + "password=" + this.password, { headers: { 'content-type': 'application/x-www-form-urlencoded' } }).

              then(response => {
                  console.log('signed in!!!');
                  window.location.href = "/accounts.html";                   
                  this.createAccount();
              })
      },
      createAccount() {
          axios.post('/api/clients/current/accounts', { headers: { 'content-type': 'application/x-www-form-urlencoded' } })
              .then(response => {

                  window.location.href = "/accounts.html";

              })
              .catch(function (error) {
                  if (error.response) {
                      console.log("ENTRA AL ERROR");

                      console.log(error.response.data);
                      console.log(error.response.status);
                      console.log(error.response.headers);
                  } else if (error.request) {
                      console.log(error.request);
                  } else {
                      console.log('Error', error.message);
                  }
                  console.log(error.config);
              });
      },
    
  }


});


/*
Swal.fire({
  icon: 'success',
  text: 'Usuario creado con exito!',
  showConfirmButton: false
});}*/