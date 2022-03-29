let account = new Vue({
  el: '#account',
  data: {
    cuenta:{},
    transactions:[]      
  },

  created() {
    this.loadData();
    console.log(this.cuenta);
  },

  methods: {

    loadData() {

      const valores = window.location.search;

      const urlParams = new URLSearchParams(valores);

      var id = urlParams.get('id');

      axios.get(`api/accounts/${id}`)
        .then(function (response) {
          account.cuenta = response.data;            
          account.transactions=response.data.transactions
          console.log(account.cuenta);
        })
        .catch(function (error) {
          console.log(error);
        })
        .then(function () {
        });
    },      
  },

  computed:{
    ordenarTransacciones(){
      var orderArray=account.transactions;
      console.log(orderArray);
      orderArray.sort(function (a, b) {
        if (b.id > a.id) {
          return 1;
        }
        if (b.id < a.id) {
          return -1;
        }
        return 0;
      });
      return orderArray;
    }
  },
});