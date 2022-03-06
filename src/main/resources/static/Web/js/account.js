var app2 = new Vue({
  el: "#app2",
  data: {
    accounts: [],
    saludo: "Hola",
  },
  created() {
    this.loadData();
  },
  methods: {
    addClients() {
      this.postClient();
    },



    loadData() {
      const urlParams = new URLSearchParams(window.location.search);
             const number = urlParams.get('number');

      axios.get(`http://localhost:8080/api/accounts/${number}`, { mode: 'no-cors'}).then((response) => {
      // axios.get("http://localhost:8080/api/accounts", { mode: 'no-cors'}).then((response) => {
        this.accounts = response.data.transactions;
        console.log(this.accounts);
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

  },


});
