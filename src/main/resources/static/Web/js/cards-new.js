var app = new Vue({
  el: "#app",
  data: {
    cards: [],
    color: "",
    type: "",
    debit: [],
    credit: [],
  },

  created() {
      this.loadData()
  },
  methods: {
    loadData() {
        axios.get('http://localhost:8080/api/clients/current')
        .then((response) => {    
                this.cards = response.data.card;
                console.log(this.cards);

            this.debit = this.cards.filter(card => card.type == 'DEBIT');
            this.credit = this.cards.filter(card => card.type == 'CREDIT');
            })
            .catch(function(error) {
                console.log(error);
            })
    },

    crearTarjeta() {
      axios
        .post(
          "/api/clients/current/cards",
          "type=" + this.type + "&color=" + this.color,
          { headers: { "content-type": "application/x-www-form-urlencoded" } }
        )
        .then((res) => {
          window.location.href = "/web/cards2.html";
        })
        .catch((e) => {
          this.mensaje = !this.mensaje;
          console.log(e);
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
