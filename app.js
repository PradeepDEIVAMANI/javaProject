// ==========================================
// APEX CORE BANKING — CLIENT LEDGER ENGINE
// Faithful browser deployment of Java Core Banking System
// ==========================================

const STORAGE_KEY = 'apex_banking_database_v1';

// Seed Database matching Java tables (login, login2, login3, bank, signup, signup2)
const SEED_DATA = {
  // Customers (mapped from login & signup tables)
  customers: [
    {
      accNo: '1001',
      pin: '1234',
      fname: 'Pradeep',
      lname: 'Deivamani',
      dob: '1996-08-22',
      gender: 'Male',
      email: 'pradeep@example.com',
      city: 'Paris',
      accType: 'Savings',
      balance: 14500.00
    },
    {
      accNo: '1002',
      pin: '2345',
      fname: 'Alice',
      lname: 'Dubois',
      dob: '1994-03-11',
      gender: 'Female',
      email: 'alice.d@banque.fr',
      city: 'Lyon',
      accType: 'Current',
      balance: 8250.00
    },
    {
      accNo: '1003',
      pin: '3456',
      fname: 'Marco',
      lname: 'Rossi',
      dob: '1991-11-05',
      gender: 'Male',
      email: 'marco.r@fintech.it',
      city: 'Milan',
      accType: 'Savings',
      balance: 19800.00
    }
  ],

  // Front Office Staff (login2 table)
  frontOffStaff: [
    { staffId: '2001', pass: 'staff123', name: 'Front-Desk Officer Sarah' }
  ],

  // Branch Managers (login3 table)
  managers: [
    { mgrId: '9001', pass: 'admin123', name: 'Branch Manager Thomas Laurent' }
  ],

  // Historical ledger transactions (bank table in Java: Password, Date, Type, Number)
  transactions: [
    {
      id: 'TXN-901',
      accNo: '1001',
      type: 'Deposit',
      amount: 5000.00,
      description: 'Initial Opening Deposit',
      date: '2026-10-15 09:30:00',
      runningBalance: 5000.00
    },
    {
      id: 'TXN-902',
      accNo: '1001',
      type: 'Deposit',
      amount: 10000.00,
      description: 'Direct Payroll Wire Transfer',
      date: '2026-10-28 14:15:22',
      runningBalance: 15000.00
    },
    {
      id: 'TXN-903',
      accNo: '1001',
      type: 'Withdrawal',
      amount: 500.00,
      description: 'ATM Cash Withdrawal',
      date: '2026-11-02 18:40:10',
      runningBalance: 14500.00
    },
    {
      id: 'TXN-904',
      accNo: '1002',
      type: 'Deposit',
      amount: 8250.00,
      description: 'Account Setup Capital',
      date: '2026-10-20 11:00:00',
      runningBalance: 8250.00
    },
    {
      id: 'TXN-905',
      accNo: '1003',
      type: 'Deposit',
      amount: 20000.00,
      description: 'Initial Deposit & Investment Bond',
      date: '2026-10-22 16:45:00',
      runningBalance: 20000.00
    },
    {
      id: 'TXN-906',
      accNo: '1003',
      type: 'Withdrawal',
      amount: 200.00,
      description: 'ATM Quick Cash Withdrawal',
      date: '2026-11-04 12:10:00',
      runningBalance: 19800.00
    }
  ]
};

// State
let db = loadDatabase();
let currentUser = null; // { role: 'customer' | 'frontoff' | 'manager', data: ... }

// Audio Synth for Real-Time ATM sounds
function playAudioTone(freq, type = 'sine', duration = 0.08) {
  try {
    const ctx = new (window.AudioContext || window.webkitAudioContext)();
    const osc = ctx.createOscillator();
    const gain = ctx.createGain();
    osc.type = type;
    osc.frequency.value = freq;
    gain.gain.setValueAtTime(0.08, ctx.currentTime);
    gain.gain.exponentialRampToValueAtTime(0.0001, ctx.currentTime + duration);
    osc.connect(gain);
    gain.connect(ctx.destination);
    osc.start();
    osc.stop(ctx.currentTime + duration);
  } catch (e) {
    // Audio context not allowed or unsupported
  }
}

function playSuccessChime() {
  playAudioTone(523.25, 'triangle', 0.1);
  setTimeout(() => playAudioTone(659.25, 'triangle', 0.12), 80);
  setTimeout(() => playAudioTone(783.99, 'triangle', 0.16), 160);
}

function playErrorTone() {
  playAudioTone(220, 'sawtooth', 0.15);
  setTimeout(() => playAudioTone(196, 'sawtooth', 0.2), 100);
}

// Database Helpers
function loadDatabase() {
  const stored = localStorage.getItem(STORAGE_KEY);
  if (stored) {
    try {
      return JSON.parse(stored);
    } catch (e) {
      console.warn('Error reading stored DB, using seeds:', e);
    }
  }
  saveDatabase(SEED_DATA);
  return JSON.parse(JSON.stringify(SEED_DATA));
}

function saveDatabase(data) {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(data));
  db = data;
}

function resetDatabase() {
  if (confirm('Reset database to default seed data? All custom transactions will be cleared.')) {
    saveDatabase(SEED_DATA);
    showToast('Database reset to factory seeds.');
    if (currentUser) {
      logout();
    } else {
      location.reload();
    }
  }
}

// Format Currency
function formatCurrency(num) {
  return '€' + Number(num).toLocaleString('en-US', {
    minimumFractionDigits: 2,
    maximumFractionDigits: 2
  });
}

// Format Timestamp
function getTimestamp() {
  const now = new Date();
  const pad = (n) => String(n).padStart(2, '0');
  return `${now.getFullYear()}-${pad(now.getMonth() + 1)}-${pad(now.getDate())} ${pad(now.getHours())}:${pad(now.getMinutes())}:${pad(now.getSeconds())}`;
}

// Toast Notifications
function showToast(msg) {
  const toast = document.getElementById('toast');
  toast.textContent = msg;
  toast.classList.remove('hidden');
  setTimeout(() => toast.classList.add('hidden'), 3500);
}

// Navigation & Views
function switchView(viewId) {
  document.querySelectorAll('.view-panel').forEach(panel => {
    panel.classList.remove('active');
  });
  const target = document.getElementById(viewId);
  if (target) target.classList.add('active');
  window.scrollTo({ top: 0, behavior: 'smooth' });
}

function showLoginView() {
  switchView('viewLogin');
  updateHeaderUserPill();
}

function showSignupView() {
  switchView('viewSignup');
}

function updateHeaderUserPill() {
  const pill = document.getElementById('currentUserBadge');
  const roleTag = document.getElementById('userRoleTag');
  const nameDisp = document.getElementById('userNameDisplay');

  if (!currentUser) {
    pill.classList.add('hidden');
  } else {
    pill.classList.remove('hidden');
    roleTag.textContent = currentUser.role.toUpperCase();
    nameDisp.textContent = currentUser.data.fname 
      ? `${currentUser.data.fname} ${currentUser.data.lname}`
      : currentUser.data.name;

    if (currentUser.role === 'manager') roleTag.style.background = '#f59e0b';
    else if (currentUser.role === 'frontoff') roleTag.style.background = '#06b6d4';
    else roleTag.style.background = '#6366f1';
  }
}

// Authentication (Java Login.java logic)
function handleLoginSubmit(event) {
  event.preventDefault();
  const acc = document.getElementById('accNumInput').value.trim();
  const pass = document.getElementById('passInput').value.trim();
  const errBanner = document.getElementById('loginError');
  errBanner.classList.add('hidden');

  // Check 1: Customer table (login)
  const cust = db.customers.find(c => c.accNo === acc && c.pin === pass);
  if (cust) {
    currentUser = { role: 'customer', data: cust };
    playSuccessChime();
    showToast(`Logged in as Customer: ${cust.fname} ${cust.lname}`);
    renderCustomerView();
    switchView('viewCustomer');
    updateHeaderUserPill();
    return;
  }

  // Check 2: Front Office (login2)
  const staff = db.frontOffStaff.find(s => s.staffId === acc && s.pass === pass);
  if (staff) {
    currentUser = { role: 'frontoff', data: staff };
    playSuccessChime();
    showToast(`Staff access granted: ${staff.name}`);
    renderFrontOffView();
    switchView('viewFrontOff');
    updateHeaderUserPill();
    return;
  }

  // Check 3: Manager (login3)
  const mgr = db.managers.find(m => m.mgrId === acc && m.pass === pass);
  if (mgr) {
    currentUser = { role: 'manager', data: mgr };
    playSuccessChime();
    showToast(`Manager portal unlocked: ${mgr.name}`);
    renderManagerView();
    switchView('viewManager');
    updateHeaderUserPill();
    return;
  }

  // Not found
  playErrorTone();
  errBanner.textContent = 'Invalid credentials. Please verify Account / Staff Number and PIN.';
  errBanner.classList.remove('hidden');
}

function quickLogin(role) {
  if (role === 'customer') {
    document.getElementById('accNumInput').value = '1001';
    document.getElementById('passInput').value = '1234';
  } else if (role === 'frontoff') {
    document.getElementById('accNumInput').value = '2001';
    document.getElementById('passInput').value = 'staff123';
  } else if (role === 'manager') {
    document.getElementById('accNumInput').value = '9001';
    document.getElementById('passInput').value = 'admin123';
  }
  handleLoginSubmit(new Event('submit'));
}

function logout() {
  currentUser = null;
  updateHeaderUserPill();
  document.getElementById('accNumInput').value = '';
  document.getElementById('passInput').value = '';
  document.getElementById('loginError').classList.add('hidden');
  showLoginView();
  showToast('Logged out of system.');
}

// CUSTOMER VIEW RENDERING
function renderCustomerView() {
  if (!currentUser || currentUser.role !== 'customer') return;
  const cust = db.customers.find(c => c.accNo === currentUser.data.accNo);
  currentUser.data = cust;

  document.getElementById('customerNameHeading').textContent = `${cust.fname}`;
  document.getElementById('customerBalanceDisplay').textContent = formatCurrency(cust.balance);
  document.getElementById('customerAccMeta').textContent = `Account: #${cust.accNo} · ${cust.accType} Account`;

  // Render mini-statement
  const txns = db.transactions
    .filter(t => t.accNo === cust.accNo)
    .sort((a, b) => new Date(b.date) - new Date(a.date));

  const tbody = document.getElementById('customerLedgerBody');
  tbody.innerHTML = '';

  if (txns.length === 0) {
    tbody.innerHTML = `<tr><td colspan="6" style="text-align:center; color: #64748b; padding: 24px;">No transaction records found.</td></tr>`;
    return;
  }

  txns.forEach(t => {
    const isCredit = t.type === 'Deposit';
    const amountClass = isCredit ? 'amount-credit' : 'amount-debit';
    const sign = isCredit ? '+' : '-';

    const row = document.createElement('tr');
    row.innerHTML = `
      <td><code>${t.date}</code></td>
      <td><strong>${t.type}</strong></td>
      <td>${t.description}</td>
      <td class="${amountClass}">${sign}${formatCurrency(t.amount)}</td>
      <td><code>${formatCurrency(t.runningBalance)}</code></td>
      <td><span class="status-badge status-success">Settled</span></td>
    `;
    tbody.appendChild(row);
  });
}

// BRANCH MANAGER VIEW RENDERING (Manager.java, Mdeposit.java, Mactivities.java)
function renderManagerView() {
  const totalDeposits = db.transactions
    .filter(t => t.type === 'Deposit')
    .reduce((sum, t) => sum + Number(t.amount), 0);

  const totalLiquidity = db.customers
    .reduce((sum, c) => sum + Number(c.balance), 0);

  document.getElementById('mgrTotalDeposits').textContent = formatCurrency(totalDeposits);
  document.getElementById('mgrTotalLiquidity').textContent = formatCurrency(totalLiquidity);
  document.getElementById('mgrTotalAccounts').textContent = db.customers.length;
  document.getElementById('mgrTotalTransactions').textContent = db.transactions.length;

  const tbody = document.getElementById('managerLedgerBody');
  tbody.innerHTML = '';

  const sortedTxns = [...db.transactions].sort((a, b) => new Date(b.date) - new Date(a.date));

  sortedTxns.forEach(t => {
    const client = db.customers.find(c => c.accNo === t.accNo);
    const clientName = client ? `${client.fname} ${client.lname}` : 'Unknown Client';
    const isCredit = t.type === 'Deposit';

    const row = document.createElement('tr');
    row.innerHTML = `
      <td><code>${t.date}</code></td>
      <td><code>#${t.accNo}</code></td>
      <td><strong>${clientName}</strong></td>
      <td>${t.type} (${t.description})</td>
      <td class="${isCredit ? 'amount-credit' : 'amount-debit'}">${isCredit ? '+' : '-'}${formatCurrency(t.amount)}</td>
      <td><code>${formatCurrency(t.runningBalance)}</code></td>
    `;
    tbody.appendChild(row);
  });
}

// FRONT OFFICE VIEW RENDERING (Forntoff.java, ClientsList.java)
function renderFrontOffView() {
  const tbody = document.getElementById('clientsListBody');
  tbody.innerHTML = '';

  db.customers.forEach(cust => {
    const row = document.createElement('tr');
    row.innerHTML = `
      <td><code>#${cust.accNo}</code></td>
      <td><strong>${cust.fname} ${cust.lname}</strong></td>
      <td>${cust.email}</td>
      <td>${cust.city}</td>
      <td>${cust.gender}</td>
      <td><span class="status-badge status-success">${cust.accType}</span></td>
      <td><strong class="amount-credit">${formatCurrency(cust.balance)}</strong></td>
      <td>
        <button class="btn btn-outline" style="padding: 4px 10px; font-size: 11px;" onclick="viewCustomerMiniStatement('${cust.accNo}')">Ledger</button>
      </td>
    `;
    tbody.appendChild(row);
  });
}

function filterClientsTable() {
  const q = document.getElementById('clientSearchInput').value.toLowerCase();
  const rows = document.querySelectorAll('#clientsListBody tr');
  rows.forEach(r => {
    const text = r.textContent.toLowerCase();
    r.style.display = text.includes(q) ? '' : 'none';
  });
}

function viewCustomerMiniStatement(accNo) {
  const cust = db.customers.find(c => c.accNo === accNo);
  if (!cust) return;
  currentUser = { role: 'customer', data: cust };
  renderCustomerView();
  switchView('viewCustomer');
  updateHeaderUserPill();
  showToast(`Switched view to client #${accNo} (${cust.fname} ${cust.lname})`);
}

// SIGNUP / NEW ACCOUNT CREATION (Signup.java, SignupTwo.java)
function handleSignupSubmit(event) {
  event.preventDefault();

  const fname = document.getElementById('newFname').value.trim();
  const lname = document.getElementById('newLname').value.trim();
  const dob = document.getElementById('newDob').value;
  const gender = document.getElementById('newGender').value;
  const email = document.getElementById('newEmail').value.trim();
  const city = document.getElementById('newCity').value.trim();
  const accType = document.getElementById('newAccType').value;
  const initialDep = parseFloat(document.getElementById('newInitialDeposit').value) || 0;
  const pin = document.getElementById('newPin').value.trim();

  // Generate unique Account Number like random in Signup.java
  const newAccNo = String(Math.floor(1000 + Math.random() * 9000));

  const newCustomer = {
    accNo: newAccNo,
    pin: pin,
    fname: fname,
    lname: lname,
    dob: dob,
    gender: gender,
    email: email,
    city: city,
    accType: accType,
    balance: initialDep
  };

  db.customers.push(newCustomer);

  // Initial deposit ledger record
  db.transactions.push({
    id: `TXN-${Math.floor(1000 + Math.random() * 9000)}`,
    accNo: newAccNo,
    type: 'Deposit',
    amount: initialDep,
    description: 'Initial Account Opening Deposit',
    date: getTimestamp(),
    runningBalance: initialDep
  });

  saveDatabase(db);
  playSuccessChime();

  alert(`🎉 Account Successfully Created!\n\nAccount Number: ${newAccNo}\nSecurity PIN: ${pin}\nOpening Balance: ${formatCurrency(initialDep)}\n\nYou can now sign in using these credentials.`);

  // Auto login to the newly created account
  currentUser = { role: 'customer', data: newCustomer };
  renderCustomerView();
  switchView('viewCustomer');
  updateHeaderUserPill();
}

// MODAL CONTROLS & TRANSACTIONS
function openModal(type) {
  playAudioTone(440, 'sine', 0.05);
  if (type === 'deposit') document.getElementById('modalDeposit').classList.remove('hidden');
  else if (type === 'withdraw') document.getElementById('modalWithdraw').classList.remove('hidden');
  else if (type === 'transfer') document.getElementById('modalTransfer').classList.remove('hidden');
  else if (type === 'pin') document.getElementById('modalPin').classList.remove('hidden');
}

function closeModal(type) {
  if (type === 'deposit') document.getElementById('modalDeposit').classList.add('hidden');
  else if (type === 'withdraw') document.getElementById('modalWithdraw').classList.add('hidden');
  else if (type === 'transfer') document.getElementById('modalTransfer').classList.add('hidden');
  else if (type === 'pin') document.getElementById('modalPin').classList.add('hidden');
}

function setDepositAmount(amt) {
  const input = document.getElementById('depositAmountInput');
  input.value = (parseFloat(input.value) || 0) + amt;
}

function setWithdrawAmount(amt) {
  document.getElementById('withdrawAmountInput').value = amt;
}

// 1. DEPOSIT (Deposit.java)
function handleDepositSubmit(event) {
  event.preventDefault();
  const amt = parseFloat(document.getElementById('depositAmountInput').value);
  if (isNaN(amt) || amt <= 0) return;

  const cust = db.customers.find(c => c.accNo === currentUser.data.accNo);
  cust.balance += amt;

  db.transactions.push({
    id: `TXN-${Math.floor(1000 + Math.random() * 9000)}`,
    accNo: cust.accNo,
    type: 'Deposit',
    amount: amt,
    description: 'ATM Cash Deposit',
    date: getTimestamp(),
    runningBalance: cust.balance
  });

  saveDatabase(db);
  playSuccessChime();
  closeModal('deposit');
  document.getElementById('depositAmountInput').value = '';
  showToast(`Successfully deposited ${formatCurrency(amt)}`);
  renderCustomerView();
}

// 2. WITHDRAW (Widthdraw.java)
function handleWithdrawSubmit(event) {
  event.preventDefault();
  const amt = parseFloat(document.getElementById('withdrawAmountInput').value);
  if (isNaN(amt) || amt <= 0) return;

  const cust = db.customers.find(c => c.accNo === currentUser.data.accNo);

  if (amt > cust.balance) {
    playErrorTone();
    alert(`Insufficient funds! Available balance is ${formatCurrency(cust.balance)}.`);
    return;
  }

  cust.balance -= amt;

  db.transactions.push({
    id: `TXN-${Math.floor(1000 + Math.random() * 9000)}`,
    accNo: cust.accNo,
    type: 'Withdrawal',
    amount: amt,
    description: 'ATM Cash Dispensed',
    date: getTimestamp(),
    runningBalance: cust.balance
  });

  saveDatabase(db);
  playSuccessChime();
  closeModal('withdraw');
  document.getElementById('withdrawAmountInput').value = '';
  showToast(`Successfully dispensed ${formatCurrency(amt)}`);
  renderCustomerView();
}

// 3. EXTERNAL TRANSFER (Externaltransaction.java)
function handleTransferSubmit(event) {
  event.preventDefault();
  const targetAcc = document.getElementById('transferTargetAcc').value.trim();
  const amt = parseFloat(document.getElementById('transferAmountInput').value);
  const note = document.getElementById('transferNoteInput').value.trim() || 'External Transfer';

  if (targetAcc === currentUser.data.accNo) {
    playErrorTone();
    alert('Cannot transfer funds to the same source account.');
    return;
  }

  const recipient = db.customers.find(c => c.accNo === targetAcc);
  if (!recipient) {
    playErrorTone();
    alert(`Recipient account #${targetAcc} does not exist in the bank directory.`);
    return;
  }

  const sender = db.customers.find(c => c.accNo === currentUser.data.accNo);
  if (amt > sender.balance) {
    playErrorTone();
    alert(`Insufficient balance. Current balance is ${formatCurrency(sender.balance)}.`);
    return;
  }

  const now = getTimestamp();

  // Debit sender
  sender.balance -= amt;
  db.transactions.push({
    id: `TXN-${Math.floor(1000 + Math.random() * 9000)}`,
    accNo: sender.accNo,
    type: 'Withdrawal',
    amount: amt,
    description: `Transfer to #${targetAcc} (${recipient.fname}) - ${note}`,
    date: now,
    runningBalance: sender.balance
  });

  // Credit recipient
  recipient.balance += amt;
  db.transactions.push({
    id: `TXN-${Math.floor(1000 + Math.random() * 9000)}`,
    accNo: recipient.accNo,
    type: 'Deposit',
    amount: amt,
    description: `Transfer received from #${sender.accNo} (${sender.fname}) - ${note}`,
    date: now,
    runningBalance: recipient.balance
  });

  saveDatabase(db);
  playSuccessChime();
  closeModal('transfer');
  document.getElementById('transferAmountInput').value = '';
  showToast(`Wire transfer of ${formatCurrency(amt)} to #${targetAcc} completed.`);
  renderCustomerView();
}

// 4. PIN CHANGE (Passwordchange.java)
function handlePinSubmit(event) {
  event.preventDefault();
  const oldPin = document.getElementById('oldPinInput').value.trim();
  const newPin = document.getElementById('newPinInput').value.trim();
  const confirmPin = document.getElementById('confirmPinInput').value.trim();

  const cust = db.customers.find(c => c.accNo === currentUser.data.accNo);

  if (cust.pin !== oldPin) {
    playErrorTone();
    alert('Incorrect current PIN entered.');
    return;
  }

  if (newPin !== confirmPin) {
    playErrorTone();
    alert('New PIN and Confirm PIN do not match.');
    return;
  }

  cust.pin = newPin;
  saveDatabase(db);
  playSuccessChime();
  closeModal('pin');
  document.getElementById('oldPinInput').value = '';
  document.getElementById('newPinInput').value = '';
  document.getElementById('confirmPinInput').value = '';
  showToast('PIN successfully updated.');
}

// 5. DOWNLOAD MINI STATEMENT (Ministatement.java)
function downloadStatement() {
  if (!currentUser || currentUser.role !== 'customer') return;
  const cust = currentUser.data;
  const txns = db.transactions.filter(t => t.accNo === cust.accNo);

  let csvContent = 'data:text/csv;charset=utf-8,';
  csvContent += 'Transaction ID,Date,Type,Description,Amount (EUR),Running Balance (EUR)\n';

  txns.forEach(t => {
    csvContent += `"${t.id}","${t.date}","${t.type}","${t.description}",${t.amount},${t.runningBalance}\n`;
  });

  const encodedUri = encodeURI(csvContent);
  const link = document.createElement('a');
  link.setAttribute('href', encodedUri);
  link.setAttribute('download', `Mini_Statement_${cust.accNo}.csv`);
  document.body.appendChild(link);
  link.click();
  document.body.removeChild(link);
  showToast('Statement exported to CSV.');
}

// Initialization on load
document.addEventListener('DOMContentLoaded', () => {
  // Pre-fill demo customer credentials in form for easy immediate testing
  document.getElementById('accNumInput').value = '1001';
  document.getElementById('passInput').value = '1234';
});
