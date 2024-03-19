import Vue from "vue";
import store from '../../store/store'

async function showAlert(title, text, type, confirmButtonText, onConfirm) {
  return Vue.swal({
      title: title,
      text: text,
      icon: type,
      showCancelButton: true,
      confirmButtonText: confirmButtonText,
      confirmButtonColor: "#212529",

  }).then(async (result) => {
      if (result.isConfirmed) {
          try {
              store.dispatch('changeStatusOverlay');
              const response = await onConfirm();
              store.dispatch('changeStatusOverlay');

              Vue.swal({
                  title: 'Exito!',
                  icon: 'success',
                  timer: 2000,
              }).then(()=>{
                  window.location.reload()
              });

          } catch (error) {
              console.error(error);
              store.dispatch('changeStatusOverlay');
          }
      }
  });
}

export function showWarningAlert  (title, text, confirmButtonText, onConfirm) {
  return showAlert(title, text, 'success', confirmButtonText, onConfirm);
}

export function showInfoAlert  (title, text, confirmButtonText, onConfirm) {
  return showAlert(title, text, 'question', confirmButtonText, onConfirm);
}