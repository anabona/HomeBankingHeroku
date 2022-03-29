

let app = new Vue({
  el: '#app',
  data: {
    clients: [],
    json: [],
    newClientName: '',
    newClientLastName: '',
    newClientEmail: '',
    newClientPassword:'',
  },

  created() {
    this.loadData();
  },

  methods: {

    loadData() {

      axios.get('./rest/clients')
        .then(function (response) {
          app.clients = response.data._embedded.clients;
          app.json = response;
        })
        .catch(function (error) {
          console.log(error);
        })
        .then(function () {
        });
    },

    addClient: function () {
      if (this.newClientName != "" && this.newClientLastName != "" && this.newClientEmail != "" && this.newClientPassword!="") {
        console.log("entra al if"); 
        this.postClient();
        console.log("postea el cliente");
        app.loadData();
        this.reiniciarValores();
      }
      else {
        Swal.fire({
          icon: 'error',
          text: 'Se detectaron campos sin rellenar!',
          showConfirmButton: false
        });
      }
    },
    postClient() {
      axios.post('rest/clients', {
        firstName: this.newClientName,
        lastName: this.newClientLastName,
        email: this.newClientEmail,
        password: this.newClientPassword,
      })
      .then((response) => {
          Swal.fire({
            icon: 'success',
            text: `Se agregó el cliente ${response.data.firstName} ${response.data.lastName}`,
            showConfirmButton: true
      });          
       this.loadData(); })

    },
    reiniciarValores() {
      this.newClientName = '';
      this.newClientLastName = '';
      this.newClientEmail = '';
    },
    deleteClient(url) {
      
      Swal.fire({
        title: 'Estás seguro/a?',
        text: "No podrás volver atrás si borras el usuario!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Si, borrarlo!'
      }).then((result) => {
        if (result.isConfirmed) {
          axios.delete(url).then((response) => {        
        this.loadData();
      }).catch((error) => {
        console.log(error.response.data)
      });
          Swal.fire(        
            'Borrado!',
            'El usuario ha sido borrado con éxito.',
            'success'
          )
        }
      })

      
    }
  }
});


function loadData() {

  axios.get('/clients')
    .then(function (response) {
      app.clients = response.data._embedded.clients;
    })
    .catch(function (error) {
      console.log(error);
    })
    .then(function () {
    });
}