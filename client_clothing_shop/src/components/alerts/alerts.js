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
      cancelButtonText: "Cancelar",

  }).then(async (result) => {
      if (result.isConfirmed) {
          try {
              await store.dispatch('changeStatusOverlay');
              const response = await onConfirm();
              await store.dispatch('changeStatusOverlay');

              if(response !== undefined) {
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
              }

          } catch (error) {
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

export function showSuccessToast(title, text) {
    return showToast(title, text, 'success');
}

export function showWarningToast  (title, text) {
    return showToast(title, text, 'warning');
}

export function showInfoAlert  (title, text, confirmButtonText, onConfirm) {
  return showAlert(title, text, 'question', confirmButtonText, onConfirm);
}