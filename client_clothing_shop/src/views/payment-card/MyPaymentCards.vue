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
              <template>
                <b-dropdown variant="link" class="status-dropdown" right no-caret>
                  <template #button-content>
                    <font-awesome-icon :icon="cardStatusIcon(paymentCard.status)" :class="`status-icon status-${paymentCard.status.toLowerCase()}`"/>
                  </template>
                  <b-dropdown-item @click="updateCardStatus(paymentCard.idPaymentCard, 'Predeterminada')">Predeterminada</b-dropdown-item>
                  <b-dropdown-item @click="updateCardStatus(paymentCard.idPaymentCard, 'Habilitada')">Habilitada</b-dropdown-item>
                  <b-dropdown-item @click="updateCardStatus(paymentCard.idPaymentCard, 'Deshabilitada')">Deshabilitada</b-dropdown-item>
                </b-dropdown>
              </template>
            </div>
            <div class="card-body">
              <div class="card-number">Terminada en {{ getLastFourDigits(paymentCard.cardNumber) }}</div>
              <div class="card-expiration">Vencimiento: {{ paymentCard.expirationDate }}</div>
            </div>
            <div class="card-footer">
              <b-button variant="link" class="delete-button" @click="deleteCard(paymentCard.idPaymentCard)">Eliminar</b-button>
            </div>
          </div>
          <div class="card-item card-add" @click="openAddCardModal">
            <div class="card-add-icon">
              <font-awesome-icon icon="plus-circle" />
            </div>
            <div class="card-add-text">Añadir tarjeta</div>
          </div>
        </div>
      </b-col>
    </b-row>
    <AddCardModal @cardAdded="refreshCards"/>
  </section>
</template>

<script>
import Vue from "vue";
import PaymentCardService from "@/services/payment-card/PaymentCardService";
import {showInfoAlert, showSuccessToast, showWarningToast} from "@/components/alerts/alerts";

export default Vue.extend({
  name: "MyPaymentCards",
  components: {
    AddCardModal: () => import("@/views/payment-card/AddCardModal.vue"),
  },
  data() {
    return {
      paymentCards: [],
    }
  },
  methods: {
    async getUserPaymentCards() {
      const payload = {
        email: this.$store.getters.getEmail
      };
      this.showOverlay()
      const response = await PaymentCardService.getPaymentCardsByUserEmail(payload);
      this.showOverlay()
      if (response.status === 200) {
        this.paymentCards = response.data.content;
        console.log(this.paymentCards)
      }
    },
    openAddCardModal() {
      this.$nextTick(() => {
        this.$bvModal.show("addCardModal");
      });
    },
    async updateCardStatus(idPaymentCard, status) {
      const payload = {
        idCard: idPaymentCard,
        status: status,
      }
      const response = await PaymentCardService.putPaymentCardStatus(payload);
      if (response === 200) {
        showSuccessToast("Estado de la tarjeta actualizado correctamente")
        await this.getUserPaymentCards()
      } else {
        showWarningToast("Error al actualizar el estado de la tarjeta")
      }
    },
    async deleteCard(idPaymentCard) {
      await showInfoAlert(
          "Eliminar tarjeta",
          "¿Estás seguro de que deseas eliminar esta tarjeta?",
          "Sí, eliminar",
          async () => {
            const payload = {
              idPaymentCard: idPaymentCard,
              email: this.$store.getters.getEmail,
            }
            const response = await PaymentCardService.deletePaymentCard(payload);
            console.log(response)
            if (response === 200) {
              showSuccessToast("Tarjeta eliminada correctamente")
              await this.getUserPaymentCards()
            } else if (response === 409) {
              Vue.swal({
                title: "Operación no permitida",
                text: "Debido a politicas de devolucón, debes tener al menos una tarjeta registrada.",
                icon: "error",
                confirmButtonText: "Aceptar",
                confirmButtonColor: "#212529",
              })
            } else {
              showWarningToast("Error al eliminar la tarjeta")
            }
          }
      )
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
    },
    showOverlay() {
      this.$store.dispatch('changeStatusOverlay')
    },
    refreshCards() {
      this.getUserPaymentCards()
    },
cardStatusIcon(status) {
      if (status === 'Predeterminada') {
        return "fa-solid fa-star"
      } else if (status === 'Habilitada') {
        return "fa-solid fa-check"
      } else {
        return "fa-solid fa-times"
      }
    },
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
  position: relative;
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
.delete-button {
  color: #dc3545;
  font-weight: bold;
  transition: color 0.2s ease-in-out;
}

.delete-button:hover {
  color: #c82333;
  text-decoration: underline;
}
.status-icon {
  font-size: 24px;
  color: #fff;
}
.status-predeterminada { color: gold; }
.status-habilitada { color: green; }
.status-deshabilitada { color: red; }
.status-dropdown {
  position: absolute;
  top: 10px;
  right: 10px;
  border: none;
}
</style>
