var app = new Vue({
  el: "#app",
  data: {
    clients: [],
    loans: [],
    saludo: "Melba Morel",
  },
  created() {
    this.loadData();
  },
  methods: {
    addClients() {
      this.postClient();
    },

    loadData() {
      axios.get("http://localhost:8080/api/clients/current", {mode: 'no-cors'}).then((response) => {
        console.log(response);
        this.clients = response.data.accounts;
        this.loans = response.data.clientLoan;

      });
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

    crearCuenta(){
      axios.post("/api/clients/current/accounts", {headers:{'content-type':'application/x-www-form-urlencoded'}})
      .then(res => {
          location.reload()
          
      })
      .catch(e => {
          this.mensaje= !this.mensaje;
          console.log(e);
      })
    },

  },
});
