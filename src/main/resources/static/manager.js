var app = new Vue({
  el: "#app",
  data: {
    clients: [],
    fname: "",
    lname: "",
    email: "",
  },

  created() {
    this.loadData();
  },

  methods: {
    addClients() {
      this.postClient();
    },

    loadData() {
      axios.get("/clients").then((response) => {
        console.log(response);
        this.clients = response.data._embedded.clients;
      });
    },

    postClient() {
      if (this.fname != "" && this.lname != "" && this.email.includes("@")) {
        axios.post("/clients", {
          firstName: this.fname,
          lastName: this.lname,
          email: this.email,
        });
      }
    },

    deleteClient(url) {
      console.log(this.clients);
      console.log(url);
      axios
        .delete(url)
        .then((response) => {
          this.loadData();
        })
        .catch((error) => {
          console.log(error.response.data);
        });
    },
  },
});
