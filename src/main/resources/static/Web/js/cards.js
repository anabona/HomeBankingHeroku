 var app = new Vue({
    el: '#app',
    data: {
        cards: [],
        message: 'hola',
        debit: [],
        credit:[],
        date: 0,
    },

    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios.get('http://localhost:8080/api/clients/current')
            .then((response) => {    
                    this.cards = response.data.card;
                    console.log(this.cards);

                this.debit = this.cards.filter(element => element.type == 'DEBIT');
                this.credit = this.cards.filter(element => element.type == 'CREDIT');
                })
                .catch(function(error) {
                    console.log(error);
                })
        },

        crearTarjeta(){
            axios.post("/api/clients/current/cards", {headers:{'content-type':'application/x-www-form-urlencoded'}})
            .then(res => {
                //location.reload()
                
                window.location.href = "/web/cards2.html";
            })
            .catch(e => {
                this.mensaje= !this.mensaje;
                console.log(e);
            })
          },

          toUpper(str){
              return str.toUpperCase();
          },

          deleteCard(id) {
            console.log(this.cards);
            console.log(id);
            axios
              .patch(`/api/clients/current/cards/delete/${id}`, "status=false")
              .then((response) => {
                //this.loadData();
                window.location.reload();
              })
              .catch((error) => {
                console.log(error.response.data);
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

          tarjetaVencida(thruDate) {
            //date = new Date();
            //year = date.getFullYear();
            //month = date.getMonth() + 1;
            //day = date.getDate();
           //document.getElementById("current_date").innerHTML = year + "-" + month + "-" + day;
        
            let vencimientoTarjeta = this.cut(thruDate,5,2) + "-"+this.cut(thruDate,2,2);
            let fechaActual = new Date();

            fechaActual=String(fechaActual.getMonth()+1).padStart(2,'0')+'-'+credit_cards.cut(fechaActual.getFullYear(),2,2);

            if(fechaActual>=vencimientoTarjeta){
              "Su tarjeta está vencida"
            } return false;
             
            
          },
    }

});
 

  
