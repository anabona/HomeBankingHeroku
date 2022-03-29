let credit_cards = new Vue({
  el: '#credit_cards',
  data: {
    cuentas: [],
    clients: [],
    cards: [],
    debitCards: [],
    creditCards: [],
    tarjetasActivas:[]
  },

  created() {
    this.loadData();
    this.loadClients();
  },

  methods: {

    loadData() {
      axios.get('api/clients/current')
        .then(function (response) {
          console.log(response);
          credit_cards.cuentas = response.data.accounts;
          credit_cards.cards = response.data.cards;
          credit_cards.obtainsCreditCards();
          credit_cards.obtainsDebitCards();
          console.log(credit_cards.cards);
          credit_cards.cardsActivas();
        })
        .catch(function (error) {
          console.log(error);
        })
        .then(function () {
        });
    },
    cardsActivas(){
      credit_cards.tarjetasActivas = credit_cards.cards.filter(card=>card.esActiva);      
    },
    toUpper(word) {
      return word.toUpperCase();
    },
    cut(date, inicio, largo) {
      return date.toString().substr(inicio, largo);
    },
    obtainsDebitCards() {
      credit_cards.cards.forEach(element => {
        if (element.cardType == "DEBIT") {
          credit_cards.debitCards.push(element);
        }
      });
    },
    obtainsCreditCards() {
      credit_cards.cards.forEach(element => {
        if (element.cardType == "CREDIT") {
          credit_cards.creditCards.push(element);
        }
      });
    },
    loadClients() {
      axios.get('./rest/clients')
        .then(function (response) {
          console.log(response);
          credit_cards.clients = response.data._embedded.clients;

        })
        .catch(function (error) {
          console.log(error);
        })
        .then(function () {
        });
    },
    eliminarTarjeta(number) {
      console.log(number);
      Swal.fire({
        title: 'Estas seguro/a que quiere dar de baja la tarjeta?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3EBD02',
        cancelButtonColor: '#d33',
        cancelButtonText: 'Cancelar',
        confirmButtonText: 'Si, Quiero dar de baja la tarjeta!'
      }).then((result) => {
        console.log("ENTRO AL AXIOS");
        axios.patch('api/clients/delete/card',"cardNumber="+number)
          .then(response=>{
            console.log("Tarjeta eliminada con éxito");
            this.loadData();
            location.reload()
          }            
          )
          .catch(function (error) {
            console.log(error);
          })
          .then(function () {
          });
      })
    },
    tarjetaVencida(fechaTarjeta){
    
      //cut(card.thruDate,5,2) }} / {{cut(card.thruDate,2,2)
      let vencimientoTarjeta = this.cut(fechaTarjeta,5,2)+"/"+this.cut(fechaTarjeta,2,2);
      let fechaActual = new Date();
      
      fechaActual= String(fechaActual.getMonth() + 1).padStart(2, '0') + '/' + credit_cards.cut(fechaActual.getFullYear(),2,2);
      
      if(fechaActual>=vencimientoTarjeta){
        return true
      }
      return false;
    }
  },
  computed: {    
    
  }

});