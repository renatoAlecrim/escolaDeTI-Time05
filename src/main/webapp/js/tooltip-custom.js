
var public_vars = public_vars || {};

;(function($, window, undefined){

	"use strict";
        
	$(document).ready(function()
	{
		$('[data-toggle="tooltip"]').each(function(i, el)
		{
			var $this = $(el),
				placement = attrDefault($this, 'placement', 'top'),
				trigger = attrDefault($this, 'trigger', 'hover'),
				popover_class = $this.hasClass('tooltip-secondary') ? 'tooltip-secondary' : ($this.hasClass('tooltip-primary') ? 'tooltip-primary' : ($this.hasClass('tooltip-default') ? 'tooltip-default' : ''));

			$this.tooltip({
				placement: placement,
				trigger: trigger
			});

			$this.on('shown.bs.tooltip', function(ev)
			{
				var $tooltip = $this.next();

				$tooltip.addClass(popover_class);
			});
		});
                
                $('[data-toggle="popover"]').each(function(i, el)
		{
			var $this = $(el),
				placement = attrDefault($this, 'placement', 'right'),
				trigger = attrDefault($this, 'trigger', 'click'),
				popover_class = $this.hasClass('popover-secondary') ? 'popover-secondary' : ($this.hasClass('popover-primary') ? 'popover-primary' : ($this.hasClass('popover-default') ? 'popover-default' : ''));

			$this.popover({
				placement: placement,
				trigger: trigger
			});

			$this.on('shown.bs.popover', function(ev)
			{
				var $popover = $this.next();

				$popover.addClass(popover_class);
			});
		});

	});

})(jQuery, window);


