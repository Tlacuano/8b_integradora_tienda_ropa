<template>
  <section class="interface">
    <b-row>
      <b-col class="text-center">
        <h1>Tarjetas</h1>
      </b-col>
    </b-row>

    <b-row class="mt-3 container-payment-card">
      <b-col>
        <div class="card-list">
          <div v-for="(paymentCard, index) in paymentCards" :key="index" class="card-item">
            <div class="card-header">
              <font-awesome-icon :icon="typeCard(paymentCard.cardNumber)" class="card-icon"/>
            </div>
            <div class="card-body">
              <div class="card-number">Terminada en {{ getLastFourDigits(paymentCard.cardNumber) }}</div>
              <div class="card-expiration">Vencimiento: {{ paymentCard.expirationDate }}</div>
            </div>
            <div class="card-footer">
              <b-button block variant="dark" @click="openEditCardModal(paymentCard)">
                Editar
              </b-button>
            </div>
          </div>
          <div class="card-item card-add" @click="addCard">
            <div class="card-add-icon">
              <font-awesome-icon icon="plus-circle" />
            </div>
            <div class="card-add-text">Añadir tarjeta</div>
          </div>
        </div>
      </b-col>
    </b-row>
    <EditCardModal :paymentCard="selectedPaymentCard" />
  </section>
</template>

<script>
import Vue from "vue";
import PaymentCardService from "@/services/payment-card/PaymentCardService";

export default Vue.extend({
  name: "MyPaymentCards",
  components: {
    EditCardModal: () => import("@/views/payment-card/EditCardModal.vue")
  },
  data() {
    return {
      paymentCards: [],
      selectedPaymentCard: ""
    }
  },
  methods: {
    async getUserPaymentCards() {
      const payload = {
        email: this.$store.getters.getEmail
      };
      const response = await PaymentCardService.getPaymentCardsByUserEmail(payload);
      if (response.status === 200) {
        this.paymentCards = response.data.content;
        console.log(this.paymentCards);
      }
    },
    openEditCardModal(paymentCard) {
      this.selectedPaymentCard = paymentCard;
      this.$nextTick(() => {
        this.$bvModal.show("editCardModal");
      });
    },
    addCard() {
    },
    typeCard(cardNumber) {
      const firstDigit = cardNumber.charAt(0);

      if (firstDigit === '4') {
        return "fa-brands fa-cc-visa"
      } else if (firstDigit === '5') {
        return "fa-brands fa-cc-mastercard"
      } else if (firstDigit === '6') {
        return "fa-brands fa-cc-discover"
      } else {
        return "fa-solid fa-credit-card"
      }
    },
    getLastFourDigits(cardNumber) {
      return cardNumber.slice(-4);
    }
  },
  mounted() {
    this.getUserPaymentCards()
  }
})
</script>

<style scoped>
.container-payment-card {
  height: calc(100vh - 270px);
  overflow-x: hidden;
}
.card-list {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}
.card-item {
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 3px 8px rgba(0, 0, 0, 0.1);
  margin: 15px;
  max-width: 300px;
  width: 100%;
  transition: transform 0.3s ease;
}
.card-item:hover {
  transform: translateY(-5px);
}
.card-header {
  align-items: center;
  background-color: #ccc;
  border-top-left-radius: 10px;
  border-top-right-radius: 10px;
  color: black;
  display: flex;
  justify-content: center;
  padding: 20px;
}
.card-icon {
  font-size: 50px;
}
.card-body {
  padding: 20px;
  text-align: center;
}
.card-number {
  font-size: 18px;
  font-weight: bold;
  margin-bottom: 10px;
}
.card-expiration {
  color: #555;
  font-size: 14px;
}
.card-footer {
  align-items: center;
  display: flex;
  justify-content: center;
  padding: 20px;
}
.card-add {
  background-color: #f9f9f9;
  border: 2px dashed #ccc;
  border-radius: 10px;
  cursor: pointer;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 20px;
}
.card-add-icon {
  font-size: 36px;
}
.card-add-text {
  font-size: 16px;
  margin-top: 10px;
}
</style>
