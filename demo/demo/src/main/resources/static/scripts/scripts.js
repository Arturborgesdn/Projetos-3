/* =============================================
   EDENRED LANDING PAGE – landing.js
   ============================================= */

/* ── Hamburger menu ── */
const hamburger = document.getElementById('hamburger');
const navLinks  = document.querySelector('.nav-links');

hamburger.addEventListener('click', () => {
  navLinks.classList.toggle('open');
  hamburger.querySelector('i').classList.toggle('ph-list');
  hamburger.querySelector('i').classList.toggle('ph-x');
});

/* ── Scroll reveal for feature cards ── */
const cards = document.querySelectorAll('.feature-card');

const io = new IntersectionObserver(
  (entries) => {
    entries.forEach(e => {
      if (e.isIntersecting) {
        e.target.classList.add('visible');
        io.unobserve(e.target);
      }
    });
  },
  { threshold: 0.15 }
);

cards.forEach(c => io.observe(c));

/* ── Donut counter animation ── */
function animateDonut() {
  const circle = document.querySelector('.donut circle:last-child');
  const text   = document.querySelector('.donut text');
  if (!circle) return;

  const circumference = 2 * Math.PI * 30; // r=30
  const target = 0.78;
  let progress = 0;

  const timer = setInterval(() => {
    progress = Math.min(progress + 0.015, target);
    const dashArr = progress * circumference;
    circle.setAttribute('stroke-dasharray', `${dashArr} ${circumference}`);
    if (text) text.textContent = Math.round(progress * 100) + '%';
    if (progress >= target) clearInterval(timer);
  }, 16);
}

/* ── Metric number counter ── */
function animateCounter(el, end, decimals = 0, suffix = '') {
  let start  = 0;
  const step = end / 60;
  const timer = setInterval(() => {
    start = Math.min(start + step, end);
    el.textContent = start.toFixed(decimals) + suffix;
    if (start >= end) clearInterval(timer);
  }, 16);
}

/* ── Trigger animations when device enters view ── */
const device = document.getElementById('heroDevice');
const deviceIO = new IntersectionObserver(
  (entries) => {
    if (entries[0].isIntersecting) {
      animateDonut();
      const bigNum = document.querySelector('.metric-big');
      if (bigNum) animateCounter(bigNum, 12.4, 1, 'k');
      deviceIO.unobserve(device);
    }
  },
  { threshold: 0.3 }
);
if (device) deviceIO.observe(device);

/* ── Sticky nav shadow on scroll ── */
const navbar = document.querySelector('.navbar');
window.addEventListener('scroll', () => {
  navbar.style.boxShadow = window.scrollY > 10
    ? '0 2px 20px rgba(0,0,0,.1)'
    : '0 1px 8px rgba(0,0,0,.05)';
}, { passive: true });