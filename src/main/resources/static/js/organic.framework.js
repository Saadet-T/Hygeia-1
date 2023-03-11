/*organic Framework*/
;(function ($) {
    'use strict';

    let $clone_to_mobile_sidebar 	= $('.clone-to-mobile-sidebar'),
        $sidebar                    = $('#sidebar'),
    	$document					= $(document),
    	$body					    = $('body'),
    	organic_MOBILE_MENU 		= {

    		init:function(obj){
				this.wrap_container();
				this.clone_menus(obj);
				return this;
		    },

    		clone_menus: function (obj) {
				let i 					= 0,
				    panels_html_args 	= Array();

				obj.each(function () {
				    let $this               = $(this),
				        $this_menu_id       = $this.attr('id'),
				        $this_menu_clone_id = 'organic-clone-' + $this_menu_id;
				    if (!$('#' + $this_menu_clone_id).length) {
				        let $__this     = $(this),
				            this_id     = $__this.attr('id'),
				            $thisClone  = $this.clone(true);
				        $thisClone.find('.menu-item').addClass('clone-menu-item');
				        $thisClone.find('[id]').each(function () {
				            $__this.attr('id', organic_MOBILE_MENU.add_string_prefix( this_id, 'organic-clone-'));
				        });
				        $thisClone.find('.organic-menu').addClass('organic-menu-clone');
				        let $thisMainPanel = $('#organic-clone-wrap .organic-panels #organic-main-panel ul');
				        $thisMainPanel.append( $thisClone.html());
				        organic_MOBILE_MENU.insert_children( $thisMainPanel, i);
				    }
				});

				$document.on('click', '.organic-next-panel', function (e) {
		            e.preventDefault();
		            let $this     = $(this),
		                thisItem  = $this.closest('.menu-item'),
		                thisPanel = $this.closest('.organic-panel'),
		                target_id = $this.attr('href');
		            if ($(target_id).length) {
		                thisPanel.addClass('organic-sub-opened');
		                $(target_id).addClass('organic-panel-opened').removeClass('organic-hidden').attr('data-parent-panel', thisPanel.attr('id'));
		                /*Insert current panel title*/
		                let item_title          = thisItem.find('.menu-name').attr('data-title'),
		                    firstItemTitle      = '',
		                    $organic_panels     = $('.organic-panels-actions-wrap'),
		                    current_panel_title = $organic_panels.find('.organic-current-panel-title');
		                if( current_panel_title.length){
		                    firstItemTitle = current_panel_title.html();
		                }
		                if ( typeof item_title !== 'undefined') {
		                    if (current_panel_title.length) {
		                        current_panel_title.html(item_title);
		                    }else{
		                        $organic_panels.prepend('<span class="organic-current-panel-title">'+item_title+'</span>');
		                    }
		                }else {
		                    current_panel_title.remove();
		                }
		                $organic_panels.find('.organic-prev-panel').remove();
		                $organic_panels.prepend('<a data-prenttitle="'+firstItemTitle+'" class="organic-prev-panel" href="#' + thisPanel.attr('id') + '" data-cur-panel="' + target_id + '" data-target="#' + thisPanel.attr('id') + '"></a>');
		            }
		        });

		        $document.on('click', '.organic-prev-panel', function (e) {
		            e.preventDefault();
		            let $this                   = $(this),
		                cur_panel_id            = $this.attr('data-cur-panel'),
		                target_id               = $this.attr('href'),
		                current_panel_title     = $('.organic-panels-actions-wrap .organic-current-panel-title'),
		                previous_panel          = $('.organic-panels-actions-wrap .organic-prev-panel');

		            $(cur_panel_id).removeClass('organic-panel-opened').addClass('organic-hidden');
		            $(target_id).addClass('organic-panel-opened').removeClass('organic-sub-opened');

		            /*Set new back button*/
		            let new_parent_panel_id = $(target_id).attr('data-parent-panel');
		            if ( typeof new_parent_panel_id == 'undefined') {
		                let root_title = $('.organic-panels-actions-wrap').data('root_title');
		                if(typeof root_title !== 'undefined'){
		                    current_panel_title.html(root_title);
		                }else{
		                    current_panel_title.remove();
		                }
		                previous_panel.remove();
		            }else {
		                previous_panel.attr('href', '#' + new_parent_panel_id).attr('data-cur-panel', target_id).attr('data-target', '#' + new_parent_panel_id);
		                /*Insert new panel title*/
		                let item_title = $('#' + new_parent_panel_id).find('.organic-next-panel[data-target="' + target_id + '"]').closest('.menu-item').find('.menu-name').attr('data-title');
		                if ( typeof item_title !== 'undefined') {
		                    if ( !current_panel_title.length) {
		                        $('.organic-panels-actions-wrap').prepend('<span class="organic-current-panel-title">'+item_title+'</span>');
		                    }else{
		                        current_panel_title.html(item_title);
		                    }
		                }else {
		                    current_panel_title.remove();
		                }
		            }
		        });
		        setTimeout(function () {
		            $('#organic-clone-wrap .organic-panels').find('.organic-carousel').organic_init_carousel();
		        }, 1000);
			},

    		insert_children: function (obj, i) {
		        let index       = parseInt(i, 10),
		            children    = obj.find('.menu-item-has-children');
		        if ( children.length) {
		            children.each(function () {
		                let thisChildItem   = $(this),
		                	next_nav_target = 'organic-panel-' + String(index);
		                organic_MOBILE_MENU.insert_children(thisChildItem,index);
		                while ($('#' + next_nav_target).length) {
		                    index++;
		                    next_nav_target = 'organic-panel-' + String(index);
		                }
		                thisChildItem.prepend('<a class="organic-next-panel" href="#' + next_nav_target + '" data-target="#' + next_nav_target + '"></a>');
		                let submenu_html = $('<div>').append(thisChildItem.find('>.sub-menu,>.wrap-megamenu').clone()).html();
		                thisChildItem.find('> .sub-menu,> .wrap-megamenu').remove();
		                $('#organic-clone-wrap .organic-panels').append('<div id="' + next_nav_target + '" class="organic-panel organic-sub-panel organic-hidden">' + submenu_html + '</div>');
		            });
		        }
		    },

		    add_string_prefix: function(str, prefix) { 
		    	return prefix + str; 
		    },

		    wrap_container: function(){
		    	if($body.find('#organic-clone-wrap').length) return 0;
				let mb_menu	='<div id="organic-clone-wrap" class="organic-clone-wrap">';
				mb_menu +='<div class="organic-panels-actions-wrap" data-root_title="Main Panel">';
				mb_menu +='<span class="organic-current-panel-title">Main Panel</span>';
				mb_menu +='<a class="organic-close-btn organic-close-panels" href="#" data-object="open-mobile-menu">&times;</a>';
				mb_menu +='</div>';
				mb_menu +='<div class="organic-panels">';
				mb_menu +='<div id="organic-main-panel" class="organic-panel organic-main-panel">';
				mb_menu +='<ul class="depth-01"></ul>';
				mb_menu +='</div></div></div>';
		        $body.prepend(mb_menu);
		    }
    	};

	$.fn.organic_best_equal_products = function(){
        let eq_height_contain = $('.eq-height-contain');
        if ( eq_height_contain.length){
            eq_height_contain.each( function( i, e){
                $(this).organic_equal_height();
            });
        }
        window.onresize = function(event) {
            event.preventDefault();
            $.fn.organic_best_equal_products();
        };
    };

    $.fn.organic_init_carousel = function () {

        if( typeof $.fn.slick == 'undefined'){
            console.error('organic-Framework need to use slick library inside, pls make sure It was loaded before used');
            return;
        }

        /**
         * Init Carousel
         * added a new param: "slidesMargin":30
         *
         */

        $(this).not('.slick-initialized').each(function () {
            let slide           = $(this),
                default_config  = slide.attr('data-slick'),
                config          = default_config !== undefined ? JSON.parse(default_config) : { arrows: true, dots: false, slidesMargin: 0, slidesToShow: 1, infinite: true, speed: 400};

            if (config.vertical === true ) {
                config.prevArrow = '<span class="organic-icon icon-left-arrow prev"></span>';
                config.nextArrow = '<span class="organic-icon icon-arrow-right next"></span>';
            } else {
                config.prevArrow = '<span class="organic-icon icon-left-arrow prev"></span>';
                config.nextArrow = '<span class="organic-icon icon-arrow-right next"></span>';
            }

            slide.on('init', function (event, slick) {
                $(event.target).trigger( 'organic_trigger_init_slide', slick);
            });
            slide.slick(config);
        });
    };

    $.fn.organic_vertical_menu = function () {
        $(this).each( function () {
            let this_menu = $(this);
            this_menu.on('click', '.block-title', function (e) {
                e.preventDefault();
                this_menu.toggleClass('open-menu');
            });
            $document.on('reset-vertical-menu', function (e) {
                e.preventDefault();
                this_menu.removeClass('open-menu');
            });
        });
    };

    $.fn.organic_sticky_header = function () {
        $(this).each(function () {
            let _this           = $(this),
                header_height   = $('#header').height(),
                position_1      = header_height + 250,
                position_2      = header_height + 700;
            $(window).scroll( function (event) {
                event.preventDefault();
                let scrl_top = $(this).scrollTop();
                if ( scrl_top > position_1 ) {
                    _this.addClass('pre-sticky');
                    if ( scrl_top > position_2){
                        _this.addClass('is-sticky');
                    }else{
                        _this.removeClass('is-sticky');
                        $document.trigger('reset-vertical-menu');
                    }
                } else {
                    _this.removeClass('pre-sticky is-sticky');
                }
            });
        });
    };

    $.fn.organic_tab = function () {
        $(this).each(function(index,element){
            let TAB = $(this);
            TAB.on('click','.tab-link', function (e) {
                e.preventDefault();
                let $this       = $(this),
                    $tab_head   = $this.closest('li'),
                    tab_id      = $this.attr('href');
                if( !$tab_head.hasClass('active') && typeof tab_id !== undefined){
                    let $active_tab = TAB.find(tab_id);
                    if( $active_tab.length ){
                        $active_tab.siblings('.active').removeClass('active');
                        $active_tab.addClass('active').organic_equal_height();
                        $tab_head.siblings('.active').removeClass('active');
                        $tab_head.addClass('active');
                    }
                }
            });
        });
    };

    $.fn.organic_rating_form_handle = function () {
        $(this).each(function(index,element){
            $(this).on('click', '.btn-rating', function (e) {
                e.preventDefault();
                let btn = $(this);
                if( !btn.hasClass('selected')){
                    btn.siblings().removeClass('selected');
                    btn.addClass('selected');
                    btn.parents('span').addClass('rated');
                }
            });
        });
    };

    $.fn.organic_accodition_handle = function () {
        $(this).each(function(index,element){
            $(this).on('click', '.btn-expand', function (e) {
                e.preventDefault();
                let tab_element  = $(this).parents('li');
                if( !tab_element.hasClass('active')){
                    let element_actived = tab_element.siblings('.active');
                    if(element_actived.length){
                        element_actived.find('.content').slideToggle(300);
                        element_actived.removeClass('active');
                    }
                }

                tab_element.toggleClass('active');
                tab_element.find('.content').slideToggle(300);
            });
        });
    };

    $.fn.organic_countdown = function () {
        if( typeof $.fn.countdown == 'undefined'){
            console.error('organic-Framework need to use countdown library inside, pls make sure It was loaded before used');
            return;
        }
        let $this           = $(this),
            html_result     = '',
            string_format   = '';

        string_format += '<span class="days"><span class="number">%-D</span><span class="text">days</span></span>';
        string_format += '<span class="hours"><span class="number">%-H</span><span class="text">Hours</span></span>';
        string_format += '<span class="mins"><span class="number">%-M</span><span class="text">Mins</span></span>';
        string_format += '<span class="secs"><span class="number">%-S</span><span class="text">Secs</span></span>';

        html_result += '<span class="days"><span class="number">00</span><span class="text">days</span></span>';
        html_result += '<span class="hours"><span class="number">00</span><span class="text">Hours</span></span>';
        html_result += '<span class="mins"><span class="number">00</span><span class="text">Mins</span></span>';
        html_result += '<span class="secs"><span class="number">00</span><span class="text">Secs</span></span>';

        $this.on('organic_countdown', function () {
            $this.each(function () {
                let $element       = $(this),
                    the_time       = $element.data('datetime');
                $element.countdown( the_time, {elapse: true});
                $element.on('update.countdown', function (event) {
                    if ( !event.elapsed ) {
                        html_result = event.strftime(string_format);
                    }
                    $element.html(html_result);
                });
            });

        }).trigger('organic_countdown');
    };

    $.fn.organic_equal_height  = function () {
        let products = $(this).find('.contain-product');
        if(products.length){
            products.css({'height': 'auto' });
            let max = 0;
            setTimeout(function () {
                products.each(function( i, e){
                    if ( max < $(e).height() ) {
                        max = $(e).height();
                    }
                });
                products.height(max);
            }, 100);

        }
    };

    $.fn.organic_stretch_the_right_background = function () {
        let win_wth = $(window).width(),
            mb_resolution = ( win_wth < 1200 ) ;
        $(this).each(
            function (index, element) {
                let $this = $(this),
                    $bg_underground = $this.find('.bg_underground');
                if ($bg_underground.length) {
                    let src = $bg_underground.data('src');
                    if( !mb_resolution) {
                        let right_offset = $(document).width() - ($this.offset().left + $this.width());
                        if (parseInt(right_offset, 10) > 0) {
                            $bg_underground.css('right', -right_offset);
                        }
                    }
                    else{
                        $bg_underground.css('right', 0);
                    }
                    if( typeof src !== undefined) {
                        $bg_underground.css('background-image', 'url(' + src + ')');
                    }
                }
            }
        );
    };

    $.fn.organic_menu_mobile = function () {
        organic_MOBILE_MENU.init(this);
    };

    $.fn.organic_sidebar_handle = function(){
        if( $clone_to_mobile_sidebar.length){
        	if( $sidebar.length ){
	            let $sidebar_content =  $sidebar.find('.sidebar-contain');
	            $clone_to_mobile_sidebar.clone().appendTo( $sidebar_content);
	        }
	        else{
	            let html_sidebar = '',
	                $main_content = $('#main-content');
	            html_sidebar +='<div id="sidebar" class="sidebar mobile-version">';
	            html_sidebar +='<div class="organic-mobile-panels">';
	            html_sidebar +='<span class="organic-current-panel-title">Sidebar</span>';
	            html_sidebar +='<a class="organic-close-btn" href="#" data-object="open-mobile-filter">&times;</a>';
	            html_sidebar +='</div>';
	            html_sidebar +='<div class="sidebar-contain"></div>';
	            html_sidebar +='</div>';

	            if( $main_content.length){
	                $main_content.after(html_sidebar);
	                let $sidebar_content =  $('#sidebar .sidebar-contain');
	                if( $sidebar_content.length){
	                    $clone_to_mobile_sidebar.clone().appendTo($sidebar_content);
	                }
	                let $caroulsel_inside = $sidebar_content.find('.organic-carousel');
	                if( $caroulsel_inside.length){
	                    $caroulsel_inside.organic_init_carousel();
	                }
	            }
	        }
	    }
	    else{
	        if( $sidebar.length < 1){
	            $('.mobile-footer .block-sidebar').remove();
	        }
	    }
    };

    $.fn.organic_sidebar_handle();
    
}( jQuery ));

















    

    

   