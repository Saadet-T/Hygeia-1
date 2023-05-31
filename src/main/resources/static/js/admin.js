async function deleteUser(data) {
    var userId = data.getAttribute("userId");
    console.log("userID: " + userId);

    // Modal HTML oluşturma
    var modalHTML = `
        <div class="modal" id="questionModal">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">Soru</h4>
                        <button type="button" class="close" data-dismiss="modal">&times;</button>
                    </div>
                    <div class="modal-body">
                        <p>Kullanıcıyı silinmek üzere emin misiniz?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success" id="yesButton">Evet</button>
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Hayır</button>
                    </div>
                </div>
            </div>
        </div>
    `;

    // Modal HTML'i sayfaya ekleme
    document.body.insertAdjacentHTML('beforeend', modalHTML);

    // Evet butonuna tıklandığında
    $("#yesButton").off().on("click", async function() {
        try {
            // Ajax isteğini yap
            await $.ajax({
                url: "/delete_user/" + userId,
                type: 'GET',
                success: function(response) {
                    console.log("deleteUser() response: " + response);
                },
                error: function(sendData, status, error) {
                    console.error('deleteUser() istek hatası:', error);
                }
            });

            // Modal HTML'i sayfadan kaldırma
            $("#questionModal").remove();
        	$(".modal-backdrop").remove();
        	location.reload();
        } catch (error) {
            console.error('deleteUser() istek hatası:', error);
        }
    });

    // Modalı açma
    $("#questionModal").modal("show");
}