let newLoan = new Vue({
  el: '#newLoan',
  data: {
      loans: [],
      cuotasElegidas:[],
      montoMaximo:"",
      nombrePrestamo:"",
      interesElegido:""
      
  },
  created() {        
      this.getLoans();        
  },
  methods: {
      getLoans() {
          axios.get('./rest/loans')
              .then(response => {
                  console.log(response.data);
                  this.loans = response.data._embedded.loans;
              })
      },        
      reiniciarValores(){
          this.cuotasElegidas=[],
          this.montoMaximo="",
          this.nombrePrestamo=""             
      },
      crearLoan(){
          console.log(this.montoMaximo);
          axios.post("rest/loans", 
          {
              name: this.nombrePrestamo,
              maxAmount: this.montoMaximo,
              payments: this.cuotasElegidas,
              interest:this.interesElegido                
            })
          //`name=${this.nombrePrestamo}&maxAmount=${this.montoMaximo}&payments=${this.cuotasElegidas}`)
          .then(response=>{
              console.log(response.data);
              console.log("Se creo el prestamo correctamente");      
              Swal.fire({
                  icon: 'success',
                  text: "Se creo el prestamo correctamente",
                  showConfirmButton: false
              });      
              this.reiniciarValores();
              this.getLoans();
          })
          .catch(function (error) {
              if (error.response) {
                  console.log("ENTRA AL ERROR");
                  Swal.fire({
                      icon: 'error',
                      text: error.response.data,
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
  }

});