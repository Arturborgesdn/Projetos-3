document.addEventListener('DOMContentLoaded', function() {
    const track = document.getElementById('carousel-track');
    if (!track) return;
    
    const slides = Array.from(track.children);
    let currentIndex = 0;
    
    function updateCarousel() {
        track.style.transform = `translateX(-${currentIndex * 100}%)`;
        
        slides.forEach((slide, index) => {
            if (index === currentIndex) {
                slide.classList.add('active');
            } else {
                slide.classList.remove('active');
            }
        });
    }
    
    function nextSlide() {
        currentIndex = (currentIndex + 1) % slides.length;
        updateCarousel();
    }
    
    // Inicializar carrossel automático a cada 5 segundos (5000ms) conforme solicitado
    slides[0].classList.add('active');
    setInterval(nextSlide, 5000);
});
