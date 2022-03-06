var app = new Vue({
  el: "#app",
  data: {
    loans: [],
    accounts: [],
    payments: [],
    name: "",
    amount: 0,
    cuotaMensual: 0,
    cuentaDestino: 0,
    maxAmount: 0,
    cuota: 0,
  },

  created() {
    this.loanData();
    this.loanLoans();
  },
  methods: {
    loanData() {
      axios
        .get("/api/loans", { heathers: { "content-type": "application/json" } })
        .then((response) => {
          this.loans = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },

    loanLoans() {
      axios
        .get("/api/clients/current")
        .then((response) => {
          this.accounts = response.data.accounts;
        })
        .catch((error) => {
          console.log(error);
        });
    },

    createLoans() {
      axios.post("/api/loans",
        {
          name: this.name,
          amount: this.amount,
          payment: this.cuota,
          account: this.cuentaDestino,
        },
        // { heathers: { "content-type": "application/json" } }
      )
          .then(response => {
            console.log(response.status);
            console.log("created");
          })

          .catch((error) => {console.log(error)})
      
    },

    filter() {
      axios.get("/api/loans").then((response) => {
        this.payments = this.loans.filter((loan) => loan.name == this.name);
        this.maxAmount = this.payments[0].maxAmount;
        this.payments = this.payments[0].payments;
        
      });
    },

    cuotaMen() {
      cuotaMensual = cuotaMensual + (cuotaMensual * 20) / 100;
    },

    calcularCuota(){
        if(app.amount<app.maxAmount){
            // && app.cuota==6 || app.cuota==12 || app.cuota==24 || app.cuota==36 || app.cuota==48 || app.cuota==60) {
            this.cuotaMensual = (app.amount / app.cuota) * 1.2;
        } else {
        alert("El monto supera el tope del Préstamo o hay un error en el valor de la cuota");
        }
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
