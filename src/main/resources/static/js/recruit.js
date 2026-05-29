/* =====================
   1. 모집글 생성/수정
   ===================== */
async function submitRecruit(e, userId) {
    e.preventDefault();
    const formData = new FormData(e.target);
    const data = Object.fromEntries(formData);

    const isUpdate = data.id != null && data.id !== "";

    if (isUpdate) {
        await fetch(`/api/recruits/${data.id}`, {
            method: "PATCH",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                ...data,
                id: Number(data.id),
                maxCount: Number(data.maxCount)
            })
        });
    } else {
        await fetch("/api/recruits", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({
                ...data,
                maxCount: Number(data.maxCount)
            })
        });
    }

    location.href = `/recruits?userId=${userId}`;
}

/* =====================
   2. 모집글 삭제
   ===================== */
async function deleteRecruit(id, userId) {
    if (!confirm('정말 흔적을 인멸하시겠습니까?')) return;

    await fetch(`/api/recruits/${id}`, { method: "DELETE" });
    location.href = `/recruits?userId=${userId}`;
}

/* =====================
   3. 참여 신청
   ===================== */
async function joinRecruit(id, userId) {
    const response = await fetch(`/api/recruits/${id}/join?userId=${userId}`, {
        method: "POST"
    });

    if (response.ok) {
        alert('공범으로 합류했습니다! 도망은 없습니다.');
        location.href = `/recruits/${id}?userId=${userId}`;
    } else {
        alert('합류에 실패했습니다. 다음 기회를...');
    }
}

/* =====================
   4. 참여 취소
   ===================== */
async function leaveRecruit(id, userId) {
    if (!confirm('정말 참여를 취소하시겠습니까?')) return;

    const response = await fetch(`/api/recruits/${id}/leave?userId=${userId}`, {
        method: "DELETE"
    });

    if (response.ok) {
        alert('참여가 취소되었습니다.');
        location.href = `/recruits/${id}?userId=${userId}`;
    } else {
        alert('참여 취소에 실패했습니다.');
    }
}

/* =====================
   5. 댓글 작성
   ===================== */
async function submitComment(e, recruitId, userId) {

    e.preventDefault();

    const content = document.getElementById("commentContent").value;

    if (!content.trim()) {
        alert('댓글 내용을 입력해주세요.');
        return;
    }

    const response = await fetch(`/api/recruits/${recruitId}/comments?userId=${userId}`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ content: content })
    });

    if (response.ok) {
        location.href = `/recruits/${recruitId}?userId=${userId}`;
    } else {
        alert('댓글 작성에 실패했습니다.');
    }
}

/* =====================
   6. 댓글 삭제
   ===================== */
async function deleteComment(commentId, recruitId, userId) {
    if (!confirm('댓글을 삭제하시겠습니까?')) return;

    const response = await fetch(`/api/recruits/${recruitId}/comments/${commentId}?userId=${userId}`, {
        method: "DELETE"
    });

    if (response.ok) {
        location.href = `/recruits/${recruitId}?userId=${userId}`;
    } else {
        alert('댓글 삭제에 실패했습니다.');
    }
}