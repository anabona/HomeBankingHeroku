let app = new Vue({
  el: '#app',
  data: {
      monto: "",
      accounts: [],
      tipoTransferencia: "",
      cuentaDestino: "",
      cuentaOrigen: "",
      descripcionTransferencia: "",
      destinatario:"",
      
  },

  created() {
      this.loadData();
      
  },

  methods: {
      loadData() {
          axios.get('/api/clients/current')
              .then(function (response) {
                  console.log(response);
                  app.accounts = response.data.accounts;
                  console.log(response.data.accounts);
              })
              .catch(function (error) {
                 console.log(error);
              })
              .then(function () {
              });
      },

      realizarTransaccion() {
          if (this.cuentaOrigen == "" || this.cuentaDestino == "" || this.monto == 0) {
              Swal.fire({
                  icon: 'error',
                  text: 'Los datos de las cuentas, no pueden estar vacíos',
                  showConfirmButton: false
              
              });
              console.log(data)
          }
          else {

              Swal.fire({
                  title: 'Por favor confirma tu transacción',
                  text: "",
                  icon: 'warning',
                  showCancelButton: true,
                  confirmButtonColor: '#3EBD02',
                  cancelButtonColor: '#d33',
                  confirmButtonText: 'Transferir'
              }).then((result) => {
                
                  if (result.isConfirmed)
                   {
                    console.log("destino " + this.cuentaDestino +"monto " + this.monto + " origen " +this.cuentaOrigen + "descripcion " +  this.descripcionTransferencia)
                      
                    /* axios.post('/api/transactions', "originAccountNumber=" + app.cuentaOrigen + 
                      + "&destinyAccountNumber=" + app.cuentaDestino + "&strAmount=" + app.monto + 
                      "&description=" + app.descripcionTransferencia, 
                      { headers: { 'content-type': 'application/x-www-form-urlencoded' } }) */

                      axios.post('/api/transactions', 
                      "originAccountNumber=" + this.cuentaOrigen + "&destinyAccountNumber=" + this.cuentaDestino + "&strAmount=" + this.monto + "&description=" + this.descripcionTransferencia, 
                      { headers: { 'content-type': 'application/x-www-form-urlencoded' } }) 



                          .then(response => {
                              Swal.fire({
                                  icon: 'success',
                                  text: 'Transacción realizada con exito!',
                                  showConfirmButton: true
                              });
                              this.limpiarFormulario();
                              window.location.href = "/web/accounts.html";
                          })
                          .catch(error => {
                              Swal.fire({
                                  icon: 'error',
                                  text: error.response.data,
                                  showConfirmButton: true
                              });
                              this.limpiarFormulario();
                          })
                  }
              })
          }

      },
      limpiarFormulario() {
          app.monto = "";
          app.cuentaOrigen = "";
          app.cuentaDestino = "";
          app.descripcionTransferencia = "";
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
   computed:{
      /* nombreDestinatario: function (){
          nombreCuentaDestino ="";
          axios.get(`/api/account/${app.cuentaDestino}`)
          .then(response=>{
              console.log(response.data);
              nombreCuentaDestino=response.data;
              this.destinatario=response.data;
              console.log("hola");
          })
          .catch(function (error) {
              console.log(error);
          })
          .then(function () {
          });
          console.log(this.destinatario);
          return this.destinatario;
      },  */
      
  } 
});