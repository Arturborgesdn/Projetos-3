$(document).ready(function() {
    function initCarousels() {
        if ($('.focus-blocks-carousel').length > 0 && !$('.focus-blocks-carousel').hasClass('slick-initialized')) {
            $('.focus-blocks-carousel').slick({
                dots: true,
                infinite: true,
                speed: 800,
                slidesToShow: 1,
                slidesToScroll: 1,
                autoplay: true,
                autoplaySpeed: 5000,
                arrows: false,
                fade: true,
                cssEase: 'linear'
            });
        }
        
        if ($('.Keyfigure1-carou').length > 0 && !$('.Keyfigure1-carou').hasClass('slick-initialized')) {
            $('.Keyfigure1-carou').slick({
                dots: false,
                infinite: true,
                speed: 500,
                slidesToShow: 4,
                slidesToScroll: 1,
                autoplay: true,
                autoplaySpeed: 3000,
                arrows: true,
                responsive: [
                    { breakpoint: 1024, settings: { slidesToShow: 3 } },
                    { breakpoint: 768, settings: { slidesToShow: 2 } },
                    { breakpoint: 480, settings: { slidesToShow: 1 } }
                ]
            });
        }
        
        if ($('.Carousel4-carou').length > 0 && !$('.Carousel4-carou').hasClass('slick-initialized')) {
            $('.Carousel4-carou').slick({
                dots: false,
                infinite: true,
                speed: 500,
                slidesToShow: 4,
                slidesToScroll: 1,
                autoplay: true,
                autoplaySpeed: 3000,
                arrows: true,
                responsive: [
                    { breakpoint: 1024, settings: { slidesToShow: 3 } },
                    { breakpoint: 768, settings: { slidesToShow: 2 } },
                    { breakpoint: 480, settings: { slidesToShow: 1 } }
                ]
            });
        }
    }

    // Check if slick is available
    if (typeof $.fn.slick !== 'undefined') {
        initCarousels();
    } else {
        console.warn("Slick.js não detectado no bundle principal. Injetando via CDN...");
        
        // Inject Slick CSS
        $('<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>').appendTo('head');
        $('<link rel="stylesheet" type="text/css" href="https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick-theme.css"/>').appendTo('head');
        
        // Inject Slick JS
        $.getScript("https://cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js", function() {
            console.log("Slick.js carregado via CDN.");
            initCarousels();
        });
    }
});
