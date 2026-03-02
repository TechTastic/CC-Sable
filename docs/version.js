(function () {
  async function initVersionSwitcher() {
    try {
      const path = window.location.pathname;
      const parts = path.split('/').filter(Boolean);

      let basePath = '/';
      if (parts.length > 0 && !parts[0].startsWith('v')) {
        basePath = '/' + parts[0] + '/';
      }

      const response = await fetch(basePath + 'versions.json');
      const versions = await response.json();

      let currentVersion = 'latest';
      let pageSubPath = '';

      if (parts.length > 0 && parts[0].startsWith('v')) {
        currentVersion = parts[0];
        pageSubPath = parts.slice(1).join('/');
      } else if (parts.length > 1 && parts[1].startsWith('v')) {
        currentVersion = parts[1];
        pageSubPath = parts.slice(2).join('/');
      } else {
        pageSubPath = parts.slice(1).join('/');
      }

      const container = document.createElement('div');
      container.style.position = 'fixed';
      container.style.top = '10px';
      container.style.right = '20px';
      container.style.zIndex = '1000';

      const select = document.createElement('select');
      select.style.padding = '4px';

      const latestOption = document.createElement('option');
      latestOption.value = 'latest';
      latestOption.textContent = 'latest';
      if (currentVersion === 'latest') {
        latestOption.selected = true;
      }
      select.appendChild(latestOption);

      versions.forEach(v => {
        const option = document.createElement('option');
        option.value = v;
        option.textContent = v;
        if (v === currentVersion) {
          option.selected = true;
        }
        select.appendChild(option);
      });

      select.onchange = function () {
        const targetVersion = this.value;

        let targetUrl;

        if (targetVersion === 'latest') {
          targetUrl = basePath + pageSubPath;
        } else {
          targetUrl = basePath + targetVersion + '/' + pageSubPath;
        }

        window.location.href = targetUrl;
      };

      container.appendChild(select);
      document.body.appendChild(container);

    } catch (e) {
      console.warn('Version switcher failed:', e);
    }
  }

  if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', initVersionSwitcher);
  } else {
    initVersionSwitcher();
  }
})();