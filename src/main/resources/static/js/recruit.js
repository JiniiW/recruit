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