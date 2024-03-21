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
                  toast: true,
                  position: "top-end",
                  showConfirmButton: false,
                  title: 'Realizado exitosamente!',
                  icon: 'success',
                  timer: 3000,
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

function showToast(Title, Text, Icon) {
  return Vue.swal({
      toast: true,
      position: "top-end",
      showConfirmButton: false,
      title: Title,
      text: Text,
      icon: Icon,
      timer: 3000,
  });
}

export function showWarningToast  (title, text) {
    return showToast(title, text, 'warning');
}

export function showInfoAlert  (title, text, confirmButtonText, onConfirm) {
  return showAlert(title, text, 'question', confirmButtonText, onConfirm);
}